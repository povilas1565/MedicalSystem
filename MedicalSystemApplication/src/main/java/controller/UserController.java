package controller;

import dto.*;
import helpers.SecurePasswordHasher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import model.*;
import model.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin
@Api
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CentreService centreService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private PatientMedicalReportService patientMedicalReportService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PutMapping(value = "/update/{email}")
    @ApiOperation("Обновление(добавление, изменение) данных пользователя")
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO dto, @PathVariable("email") String email) {
        log.info("Changing user data with email '{}'.", email);
        User user = userService.findByEmailAndDeleted(email, false);

        if (user != null) {
            user.setUsername(dto.getUsername());
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setCity(dto.getCity());
            user.setState(dto.getState());
            user.setPhone(dto.getPhone());
            user.setDate_of_birth(dto.getDate_of_birth());
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/password/{email}", consumes = "application/json")
    @ApiOperation("Обновление(изменение) пароля")
    public ResponseEntity<Void> updatePassword(@PathVariable("email") String email, @RequestBody PasswordDTO dto) {
        log.info("Changing the password of a user with email '{}'.", email);
        System.out.println(email);
        User user = userService.findByEmailAndDeleted(email, false);

        if (user != null) {
            try {
                String oldPassword = dto.getOldPassword();
                String oldHash = SecurePasswordHasher.getInstance().encode(oldPassword);

                if (user.getPassword().equals(oldHash)) {
                    String newPassword = dto.getNewPassword();
                    String newHash = SecurePasswordHasher.getInstance().encode(newPassword);
                    user.setPassword(newHash);
                    userService.save(user);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/firstPassword/{email}", consumes = "application/json")
    @ApiOperation("Обновление(изменение) пароля")
    public ResponseEntity<Void> updateFirstPassword(@PathVariable("email") String email, @RequestBody PasswordDTO dto) {
        log.info("Adding user password with email '{}'.", email);
        User user = userService.findByEmailAndDeleted(email,false);
        if (user != null) {
            String newPassword = dto.getNewPassword();
            try {
                String hashNewPassword = SecurePasswordHasher.getInstance().encode(newPassword);
                user.setPassword(hashNewPassword);
                user.setIsFirstLog(false);
                userService.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getPatient/{email}")
    @ApiOperation("Получение данных пациентов")
    public ResponseEntity<UserDTO> getPatient(@PathVariable("email") String email) {
        log.info("Getting data of patients by email '{}'.", email);
        Patient ret = (Patient) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/getDoctor/{email}")
    @ApiOperation("Получение данных докторов")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable("email") String email)  {
        log.info("Getting data of doctors by email '{}'.", email);
        Doctor ret = (Doctor) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new DoctorDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/getNurse/{email}")
    @ApiOperation("Получение данных медработников")
    public ResponseEntity<NurseDTO> getNurse(@PathVariable("email") String email) {
        log.info("Getting data of nurses by email '{}'.", email);
        Nurse ret = (Nurse) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new NurseDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value="/getCentreAdmin/{email}")
    @ApiOperation("Получение данных администраторов центров")
    public ResponseEntity<UserDTO> getCentreAdmin(@PathVariable("email") String email) {
        log.info("Getting data of Center Administrator by email '{}'.", email);
        User ret = (User) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/getUser/{email}")
    @ApiOperation("Получение данных обычных пользователей")
    public ResponseEntity<UserDTO> getUser(@PathVariable("email") String email) {
        User ret = userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    @ApiOperation("Получение профилей пользователей по Id")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        log.info("Getting a user with id '{}'.", userId);
        User ret = userService.findUserById(Long.parseLong(userId));

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }


    @GetMapping(value = "/getAll/{role}")
    @ApiOperation("Получение всех пользователей по конкретным role")
    public ResponseEntity<List<UserDTO>> geAllUserByRole(@PathVariable("role") UserRole role) {
        log.info("Getting all users by role '{}'.", role);
        List<User> ret = userService.getAll(role);
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for (User u : ret) {
            if (!u.getDeleted()) {
                dtos.add(new UserDTO(u));
            }
        }

        if (ret == null) {
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    @ApiOperation("Получение всех пользователей")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("Getting all users.");
        List<User> ret = userService.getAll();
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for (User u : ret) {
            if (!u.getDeleted()) {
                dtos.add(new UserDTO(u));
            }
        }

        if (ret == null) {
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/patient/getMedicalRecord/{email}")
    @ApiOperation("Получение медкарт пациентов")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable("email") String email) {
        log.info("Getting patient medical records by email '{}'.", email);
        Patient patient = (Patient) userService.findByEmailAndDeleted(email, false);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MedicalRecord mr = medicalRecordService.findByPatient(patient);
        MedicalRecordDTO dto = new MedicalRecordDTO(mr);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/patient/updateMedicalRecord/{email}")
    @ApiOperation("Обновление(изменение) данных медкарт пациентов")
    ResponseEntity<Void> updateMedicalRecord(@PathVariable("email") String email, @RequestBody MedicalRecordDTO dto) {
        log.info("Changing the data of the patient's medical record with the email '{}'.", email);
        Patient patient = (Patient) userService.findByEmailAndDeleted(email, false);
        MedicalRecord record = medicalRecordService.findByPatient(patient);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (record == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        record.setBloodType(dto.getBloodType());
        record.setHeight(dto.getHeight());
        record.setWeight(dto.getWeight());
        record.setAlergies(dto.getAlergies());
        medicalRecordService.save(record);

        patient.setMedicalRecord(record);
        userService.save(patient);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
