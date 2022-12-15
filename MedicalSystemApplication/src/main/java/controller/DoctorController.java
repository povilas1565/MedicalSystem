package controller;

import dto.CentreDTO;
import dto.DateIntervalDTO;
import dto.DoctorDTO;
import dto.ReviewDTO;
import helpers.DateInterval;
import helpers.DateUtil;
import helpers.Scheduler;
import helpers.SecurePasswordHasher;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AppointmentService;
import service.CentreService;
import service.NotificationService;
import service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CentreService centreService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "/makeNewDoctor", consumes = "application/json")
    public ResponseEntity<Void> addNewDoctor(@RequestBody DoctorDTO dto) {
        Doctor d = (Doctor) userService.findByEmailAndDeleted(dto.getUser().getEmail(), false);
        Centre c = centreService.findByName(dto.getCentreName());

        if (d != null) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String pass = "";
        try {
            pass = SecurePasswordHasher.getInstance().encode("123");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Doctor doctor = new Doctor(dto);
        doctor.setPassword(pass);
        doctor.setCentre(c);
        userService.save(doctor);

        c.getDoctors().add(doctor);
        centreService.save(c);

        return new ResponseEntity<>(HttpStatus.CREATED);
}

         @PostMapping(value = "/addReview")
         public ResponseEntity<Void> addReview(@RequestBody ReviewDTO dto) {

         Doctor doctor = (Doctor) userService.findByEmailAndDeleted(dto.getDoctorEmail(), false);
         Patient patient = (Patient) userService.findByEmailAndDeleted(dto.getPatientEmail(), false);

         if (doctor == null) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         for (ReviewDoctor review : doctor.getReviews()) {
             if (review.getPatient().getEmail().equals(dto.getPatientEmail())) {
                 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
             }
         }

         ReviewDoctor rd = new ReviewDoctor(dto.getRating(), DateUtil.getInstance().now("dd-MM-yyyy"), patient);
         doctor.getReviews().add(rd);
         userService.save(doctor);

         notificationService.sendNotification(patient.getEmail(), "You rated the doctor!", "Your rating of" + dto.getRating() + "stars for the doctor" + dto.getDoctorEmail() + "it is successfully recorded! Thank you for the review!");
         notificationService.sendNotification(doctor.getEmail(), "You're rated!", "Your work is evaluated by" + dto.getPatientEmail() + "with a rating of" + dto.getRating() + "stars!");
                return new ResponseEntity<>(HttpStatus.OK);
     }

     @GetMapping(value = "/getCentre/{email}")
     public ResponseEntity<CentreDTO> getCentreByDoctor(@PathVariable("email") String email) {
        Doctor d = (Doctor) userService.findByEmailAndDeleted(email, false);

        if (d == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Centre c = centreService.findByDoctor(d);
        CentreDTO dto = new CentreDTO(c);
        return new ResponseEntity<>(dto, HttpStatus.OK);
     }

     @DeleteMapping(value = "/removeDoctor/{email}")
     public ResponseEntity<Void> removeDoctor(@PathVariable("email") String email) {
         HttpHeaders header = new HttpHeaders();
         Doctor doc = (Doctor) userService.findByEmailAndDeleted(email,false);

         if (doc == null) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         List<Appointment> appointments = appointmentService.findAllByDoctor(doc);

         if (appointments != null) {
             if (appointments.size() > 0) {
                 header.set("responseText","Doctor("+email+") it cannot be deleted because it has "+appointments.size()+" scheduled examinations");
                 return new ResponseEntity<Void>(header, HttpStatus.CONFLICT);
             }
         }
         else {
             return new ResponseEntity<Void>(header, HttpStatus.CONFLICT);
         }
         doc.setDeleted(true);
         userService.save(doc);
         return new ResponseEntity<>(HttpStatus.OK);
     }

     @GetMapping(value = "/getBusyTime/{doctor}/{date}")
     public ResponseEntity<List<DateIntervalDTO>> getBusyTime(@PathVariable("doctor") String doctorEmail, @PathVariable("date") String date) {
        Doctor d = (Doctor) userService.findByEmailAndDeleted(doctorEmail, false);

        if (d == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<DateInterval> intervals = Scheduler.getFreeIntervals(d, DateUtil.getInstance().getDate(date, "dd-MM-yyyy"));
        List<DateIntervalDTO> dtos = new ArrayList<DateIntervalDTO>();

        for (DateInterval di : intervals) {
            dtos.add(new DateIntervalDTO(di, "HH:mm"));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
     }

     @GetMapping(value = "/getFreeTime/{doctor}/{date}")
     public ResponseEntity<List<DateIntervalDTO>> getFreeTime(@PathVariable("doctor") String doctorEmail, @PathVariable("date") String date) {
        Doctor d = (Doctor) userService.findByEmailAndDeleted(doctorEmail, false);

        if (d == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<DateInterval> intervals = Scheduler.getFreeIntervals(d, DateUtil.getInstance().getDate(date,"dd-MM-yyyy"));
        List<DateIntervalDTO> dtos = new ArrayList<DateIntervalDTO>();

        for (DateInterval di : intervals) {
            dtos.add(new DateIntervalDTO(di, "HH:mm"));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
     }

}
