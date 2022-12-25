package controller;

import dto.CentreDTO;
import dto.CentreFilterDTO;
import dto.DoctorDTO;
import dto.UserDTO;
import filters.Filter;
import filters.FilterFactory;
import filters.PatientFilter;
import helpers.DateUtil;
import helpers.ListUtil;
import helpers.Scheduler;
import helpers.UserSortingComparator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/centre")
@CrossOrigin
@Api
public class CentreController {

    @Autowired
    private CentreService centreService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "/registerCentre", consumes = "application/json")
    @ApiOperation("Создание центров")
    public ResponseEntity<Void> registerCentre(@RequestBody CentreDTO dto, HttpServletRequest request) {
        Centre c = centreService.findByName((dto.getName()));

        if (c == null) {
            Centre centre = new Centre();
            centre.setName(dto.getName());
            centre.setAddress(dto.getAddress());
            centre.setCity(dto.getCity());
            centre.setDescription(dto.getDescription());
            centre.setState(dto.getState());
            centre.setDoctors(new ArrayList<>());
            centre.setHalls(new ArrayList<>());
            centre.setReviews(new ArrayList<>());
            centreService.save(centre);
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getNurse/{nurseEmail}")
    @ApiOperation("Поиск центров по работающим в них медработникам")
    public ResponseEntity<CentreDTO> getCentreFromNurse(@PathVariable("nurseEmail") String nurseEmail) {
        Nurse n = (Nurse) userService.findByEmailAndDeleted(nurseEmail,false);

        if (n == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CentreDTO dto = new CentreDTO(n.getCentre());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    @ApiOperation("Поиск всех центров")
    public ResponseEntity<CentreDTO[]> getCentres() {
        List<Centre> centres = centreService.findAllSafe();
        List<CentreDTO> centresDTO = new ArrayList<CentreDTO>();
        if (centres == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Centre c : centres) {
            CentreDTO dto = new CentreDTO(c);
            centresDTO.add(dto);
        }

        return new ResponseEntity<>(centresDTO.toArray(new CentreDTO[centresDTO.size()]), HttpStatus.OK);
    }

    @PostMapping(value = "/getAll/{date}/{type}")
    @ApiOperation("Поиск всех центров с фильтрами даты и типа")
    public ResponseEntity<CentreDTO[]> getCentresWithFilter(@RequestBody CentreFilterDTO dto, @PathVariable("date") String date, @PathVariable("type") String typeOfExamination) {
        List<Centre> centres = centreService.findAllSafe();
        List<CentreDTO> centresDTO = new ArrayList<CentreDTO>();

        if (centres == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Date realDate = DateUtil.getInstance().getDate(date,"dd-MM-yyyy");
             Filter filter = FilterFactory.getInstance().get("centre");

             for (Centre c : centres) {
                 List<Doctor> doctors = c.getDoctors();

                 for (Doctor d : doctors) {
                     int freeTime = Scheduler.getFreeIntervals(d, realDate).size();

                     if (d.IsFreeOn(realDate) && d.getType().equalsIgnoreCase(typeOfExamination) && freeTime > 0) {
                         if (filter.test(c, dto)) {
                             centresDTO.add(new CentreDTO());
                             break;
                         }
                     }
                 }
             }

             CentreDTO[] ret = centresDTO.toArray(centresDTO.toArray(new CentreDTO[centres.size()]));

             return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(value = "/getPatients/{centreName}")
    @ApiOperation("Получение пациентов центров по названиям центров")
    public ResponseEntity<List<UserDTO>> getCentrePatients(@PathVariable("centreName") String centreName) {
        Centre c = centreService.findByName(centreName);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Appointment> appointments = new ArrayList<Appointment>();
        ArrayList<UserDTO> patients = new ArrayList<UserDTO>();

        appointments = appointmentService.findAllByCentre(c);

        if (appointments.isEmpty()) {
            System.out.println("There's no check-up at that centre");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Appointment app : appointments) {
            if (app.getPatient() == null)
                continue;

            if (!ListUtil.getInstance().containsWithEmail(patients, app.getPatient().getEmail())) {
                patients.add(new UserDTO(app.getPatient()));
            }
        }

        patients.sort(new UserSortingComparator());

        System.out.println(patients.size());

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping(value = "/getPatientsByFilter/{centreName}", consumes = "application/json")
    @ApiOperation("Получение пациентов центров по названиям центром и фильтрация")
    public ResponseEntity<List<UserDTO>> getCentrePatientByFilter(@PathVariable("centreName") String centreName, @RequestBody UserDTO dto) {
        HttpHeaders header = new HttpHeaders();
        Centre c = centreService.findByName(centreName);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArrayList<UserDTO> ret = new ArrayList<UserDTO>();
        List<Appointment> appointments = new ArrayList<Appointment>();

        PatientFilter filter = (PatientFilter) FilterFactory.getInstance().get("patient");
        appointments = appointmentService.findAllByCentre(c);

        if(appointments.isEmpty())
        {
            System.out.println("There's no check-up at that centre");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for(Appointment app: appointments)
        {
            Patient p = app.getPatient();

            if(p == null)
            {
                continue;
            }

            if(!ListUtil.getInstance().containsWithEmail(ret, p.getEmail()))
            {
                if(filter.test(app.getPatient(), dto))
                {
                    ret.add(new UserDTO(app.getPatient()));
                }
            }
        }


        return new ResponseEntity<>(ret,HttpStatus.OK);

    }

    @GetMapping(value="/getDoctorsByType/{centreName}/{typeOfExamination}")
    @ApiOperation("Получение докторов по типам услуг и названиям центров")
    public ResponseEntity<List<DoctorDTO>> getClinicDoctorsByType(@PathVariable("centreName") String centreName, @PathVariable("typeOfExamination") String typeOfExamination)
    {
        Centre centre = centreService.findByName(centreName);
        if(centre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Doctor> doctors = centre.getDoctors();
        List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();

        for(Doctor doc : doctors)
        {
            if(doc.getDeleted() == false && doc.getType().equalsIgnoreCase(typeOfExamination))
            {
                DoctorDTO dto = new DoctorDTO(doc);
                dtos.add(dto);
            }
        }

        return new ResponseEntity<>(dtos,HttpStatus.OK);

    }

    @GetMapping(value="/getDoctorsByTypeAndVacation/{centreName}/{typeOfExamination}/{date}")
    @ApiOperation("Получение докторов по типам услуг, названиям центров и отпускам")
    public ResponseEntity<DoctorDTO[]> getCentreDoctorsByTypeAndVacation(@PathVariable("centreName") String centreName, @PathVariable("typeOfExamination") String typeOfExamination, @PathVariable("date") String date)
    {
        Centre centre = centreService.findByName(centreName);
        if(centre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Doctor> doctors = centre.getDoctors();
        List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();

        for(Doctor doc : doctors)
        {
            if(doc.getDeleted() == false && doc.getType().equalsIgnoreCase(typeOfExamination)
                    && doc.IsFreeOn(DateUtil.getInstance().getDate(date, "dd-MM-yyyy")))
            {
                DoctorDTO dto = new DoctorDTO(doc);
                dtos.add(dto);
            }
        }

        return new ResponseEntity<>(dtos.toArray(new DoctorDTO[dtos.size()]),HttpStatus.OK);

    }
}


