package controller;

import dto.LoginDTO;
import dto.SessionUserDTO;
import helpers.SecurePasswordHasher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Patient;
import model.RegistrationRequest;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import service.AuthService;
import service.NotificationService;
import service.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "api/auth")
@CrossOrigin
@Api
public class AuthController
{
    @Autowired
    private AuthService authService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", consumes = "application/json")
    @ApiOperation("Вход")
    public ResponseEntity<SessionUserDTO> login(@RequestBody LoginDTO dto, HttpServletResponse response) {
        HttpHeaders header = new HttpHeaders();

        User u = userService.
                findByEmailAndDeleted(dto.getEmail(),false);

        if(u == null)
        {
            header.set("responseText", "User with that email doesn't exist!");
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        if(!u.getVerified())
        {
            return new ResponseEntity<>(header, HttpStatus.UNAUTHORIZED);
        }

        String token = dto.getPassword();

        try {
            String hash = SecurePasswordHasher.getInstance().encode(token);

            if(hash.equals(u.getPassword()))
            {
                response.addCookie(new Cookie("email",u.getEmail()));
                return new ResponseEntity<>(new SessionUserDTO(u),HttpStatus.OK);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        header.set("Response","Password incorrect!");
        return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
    }


    @PutMapping(value="/verifyAccount/{email}")
    @ApiOperation("Проверка и обновление cозданного аккаунта")
    public ResponseEntity<Void> verifyAccount(@PathVariable("email") String email)
    {
        User u = userService.
                findByEmailAndDeleted(email, false);

        if(u == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(u.getVerified())
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        u.setVerified(true);
        userService.save(u);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/registerRequest",consumes = "application/json")
    @ApiOperation("Регистрация")
    public ResponseEntity<Void> requestRegistration(@RequestBody RegistrationRequest request)
    {
        RegistrationRequest req = authService.
                findByEmail(request.getEmail());

        User u = userService.findByEmailAndDeleted(request.getEmail(),false);

        if(req != null || u != null)
        {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        authService.save(new RegistrationRequest(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/confirmRegister/{email}")
    @ApiOperation("Подтверждение пароля при регистрации")
    public ResponseEntity<Void> confirmRegister(@PathVariable("email") String email, HttpServletRequest httpRequest)
    {
        RegistrationRequest req = authService.
                findByEmail(email);

        if(req == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Patient patient = new Patient(req);
        patient.setVerified(false);
        String token = patient.getPassword();

        try {
            String hash = SecurePasswordHasher.getInstance().encode(token);

            patient.setPassword(hash);
            userService.save(patient);
            String requestURL = httpRequest.getRequestURL().toString();
            String root = requestURL.split("api")[0] + req.getEmail();
            notificationService.sendNotification(req.getEmail(), "Registration Center",
                    "Your request for registration for the Medical Center has been accepted.\n\n Please confirm your registration by visiting the link:"+root);
            authService.delete(req);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping(value ="/denyRegister/{reply}")
    @ApiOperation("Отмена регистрации")
    public ResponseEntity<Void> denyRegistration(@PathVariable("reply") String reply)
    {

        String parts[]=reply.split(",", 2);

        String email=parts[0];
        String text = parts[1];

        RegistrationRequest req = authService.
                findByEmail(email);

        if(req == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            notificationService.sendNotification(req.getEmail(), "Registration Center",
                    "Your request for registration of an order for a Medical Center has been denied. The reason for the refusal is as follows: "+text);
            authService.delete(req);
        } catch (MailException e){

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/sessionUser")
    @ApiOperation("Получение и нахождения конкретного пользователя приложения")
    public ResponseEntity<SessionUserDTO> getSessionUser(@CookieValue(value = "email", defaultValue = "none") String email)
    {
        if(email == null || email == "none")
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        User user = userService.findByEmailAndDeleted(email,false);

        if(user == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        SessionUserDTO dto = new SessionUserDTO(user);

        return new ResponseEntity<SessionUserDTO>(dto,HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    @ApiOperation("Выход")
    public ResponseEntity<Void> logout(HttpServletResponse response)
    {
        Cookie cookie = new Cookie("email",null);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAllRegRequest")
    @ApiOperation("Получение всех запросов на регистрацию")
    public ResponseEntity<List<RegistrationRequest>> getRegRequests()
    {
        List<RegistrationRequest> ret = authService.getAll();

        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

}
