package controller;

import dto.*;
import helpers.SecurePasswordHasher;
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
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO dto, @PathVariable("email") String email) {
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
    public ResponseEntity<Void> updatePassword(@PathVariable("email") String email, @RequestBody PasswordDTO dto) {
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
    public ResponseEntity<Void> updateFirstPassword(@PathVariable("email") String email, @RequestBody PasswordDTO dto) {
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
    public ResponseEntity<UserDTO> getPatient(@PathVariable("email") String email) {
        Patient ret = (Patient) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/getDoctor/{email}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable("email") String email)  {
        Doctor ret = (Doctor) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new DoctorDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/getNurse/{email}")
    public ResponseEntity<NurseDTO> getNurse(@PathVariable("email") String email) {
        Nurse ret = (Nurse) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new NurseDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value="/getCentreAdmin/{email}")
    public ResponseEntity<UserDTO> getCentreAdmin(@PathVariable("email") String email) {
        User ret = (User) userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/getUser/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("email") String email) {
        User ret = userService.findByEmailAndDeleted(email, false);

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        User ret = userService.findUserById(Long.parseLong(userId));

        if (ret == null || ret.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(ret), HttpStatus.OK);
    }


    @GetMapping(value = "/getAll/{role}")
    public ResponseEntity<List<UserDTO>> geAllUserByRole(@PathVariable("role") UserRole role) {

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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
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
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable("email") String email) {
        Patient patient = (Patient) userService.findByEmailAndDeleted(email, false);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MedicalRecord mr = medicalRecordService.findByPatient(patient);
        MedicalRecordDTO dto = new MedicalRecordDTO(mr);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/patient/updateMedicalRecord/{email}")
    ResponseEntity<Void> updateMedicalRecord(@PathVariable("email") String email, @RequestBody MedicalRecordDTO dto) {
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
