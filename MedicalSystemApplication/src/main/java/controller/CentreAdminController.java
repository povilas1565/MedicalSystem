package controller;


import dto.CentreDTO;
import dto.UserDTO;
import helpers.SecurePasswordHasher;
import model.Centre;
import model.CentreAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AppointmentService;
import service.CentreService;
import service.UserService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/admins/centre")
public class CentreAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CentreService centreService;

    @Autowired
    private AppointmentService appointmentService;


    @GetMapping(value = "/getCentreFromAdmin/{email}")
    public ResponseEntity<CentreDTO> getCentreFromAdmin(@PathVariable("email") String email) {
        CentreAdmin ca = (CentreAdmin) userService.findByEmailAndDeleted(email,false);
        if (ca ==  null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CentreDTO dto = new CentreDTO(ca.getCentre());
           return new ResponseEntity<CentreDTO>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/registerCentreAdmin/{centreName}")
    public ResponseEntity<Void> registerCentreAdmin(@RequestBody UserDTO dto, @PathVariable("centreName") String centreName) {
        CentreAdmin ca = (CentreAdmin) userService.findByEmailAndDeleted(dto.getEmail(),false);

        Centre centre = centreService.findByName(centreName);
        HttpHeaders header = new HttpHeaders();

        if (centre == null) {
            header.set("Response", "Centre not found");
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        if (ca == null) {
            CentreAdmin centreAdmin = new CentreAdmin();
            centreAdmin.setUsername(dto.getUsername());
            centreAdmin.setFirstname(dto.getFirstname());
            centreAdmin.setCity(dto.getCity());
            centreAdmin.setLastname(dto.getLastname());
            centreAdmin.setState(dto.getState());
            centreAdmin.setPhone(dto.getPhone());
            centreAdmin.setDate_of_birth(dto.getDate_of_birth());
            centreAdmin.setEmail(dto.getEmail());
            centreAdmin.setVacationRequests(new ArrayList<>());
            centreAdmin.setAppointmentRequests(new ArrayList<>());
            centreAdmin.setCentre(centre);
            String token = "admin1234";

            try {
                String hash = SecurePasswordHasher.getInstance().encode(token);
                centreAdmin.setPassword(hash);
                userService.save(centreAdmin);
                centreAdmin.setCentre(centre);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                header.set("Response", "Saving failed");
                return new ResponseEntity<>(header, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            header.set("Response", "Admin with that email already exists.");
            return new ResponseEntity<>(header, HttpStatus.ALREADY_REPORTED);
        }
    }
}
