package controller;

import dto.ChatDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ChatService;
import service.DoctorService;
import service.NurseService;
import service.PatientService;

@Slf4j
@RestController
@RequestMapping(value = "api/chats")
@CrossOrigin
@Api
public class ChatController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private ChatService chatService;

    @PostMapping(value = "/addChat1/{doctor}/{patient}")
    @ApiOperation("Cоздание новых чатов между докторами и пациентами")
    public ResponseEntity<Void> addChatBetweenDoctorAndPatient(@PathVariable("doctor") String doctorEmail, @PathVariable("patient") String patientEmail, @RequestBody ChatDTO dto) {
        log.info("Creation of new chats between doctor '{}' and patient '{}'.", doctorEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();
        Patient patient = patientService.findByEmail(dto.getPatient());
        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Doctor doctor = doctorService.findByEmail(dto.getDoctor());
        if (doctor == null) {
            header.set("responseText", "doctor not found: " + doctorEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = new Chat();
        chat.setPatient(patient);
        chat.setDoctor(doctor);
        chat.setDescription(dto.getDescription());
        chat.setName(dto.getName());
        chat.setMessage(dto.getMessage());
        chat.setDateAndTime(dto.getDateAndTime());
        chatService.save(chat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/addChat2/{nurse}/{patient}")
    @ApiOperation("Cоздание новых чатов между медперсоналом и пациентами")
    public ResponseEntity<Void> addChatBetweenNurseAndPatient(@PathVariable("nurse") String nurseEmail, @PathVariable("patient") String patientEmail, @RequestBody ChatDTO dto) {
        log.info("Creation of new chats between nurse '{}' and patient '{}'.", nurseEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();
        Patient patient = patientService.findByEmail(dto.getPatient());
        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Nurse nurse = nurseService.findByEmail(dto.getNurse());
        if (nurse == null) {
            header.set("responseText", "nurse not found: " + nurseEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = new Chat();
        chat.setPatient(patient);
        chat.setNurse(nurse);
        chat.setDescription(dto.getDescription());
        chat.setName(dto.getName());
        chat.setMessage(dto.getMessage());
        chat.setDateAndTime(dto.getDateAndTime());
        chatService.save(chat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(value = "/addChat3/{nurse}/{patient}/{doctor}")
    @ApiOperation("Cоздание новых чатов")
    public ResponseEntity<Void> addChat(@PathVariable("nurse") String nurseEmail, @PathVariable("doctor") String doctorEmail, @PathVariable("patient") String patientEmail, @RequestBody ChatDTO dto) {
        log.info("Creation of new chats between nurse '{}', doctor '{}' and patient '{}'.", nurseEmail, doctorEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();

        Nurse nurse = nurseService.findByEmail(dto.getNurse());
        if (nurse == null) {
            header.set("responseText", "nurse not found: " + nurseEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Patient patient = patientService.findByEmail(dto.getPatient());
        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Doctor doctor = doctorService.findByEmail(dto.getDoctor());
        if (doctor == null) {
            header.set("responseText", "nurse not found: " + doctorEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = new Chat();
        chat.setPatient(patient);
        chat.setDoctor(doctor);
        chat.setNurse(nurse);
        chat.setDescription(dto.getDescription());
        chat.setName(dto.getName());
        chat.setMessage(dto.getMessage());
        chat.setDateAndTime(dto.getDateAndTime());
        chatService.save(chat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/getChat1/{doctor}/{patient}")
    @ApiOperation("Получение чатов между докторами и пациентами")
    public ResponseEntity<ChatDTO> getChatBetweenDoctorAndPatient(@PathVariable("doctor") String doctorEmail, @PathVariable("patient") String patientEmail) {
        log.info("Getting a chat between doctor '{}' and patient '{}'.", doctorEmail, patientEmail);
        Doctor d = doctorService.findByEmail(doctorEmail);
        Patient p = patientService.findByEmail(patientEmail);

        if (d == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Chat c = chatService.findByDoctorAndPatient(d, p);
        ChatDTO dto = new ChatDTO(c);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/getChat2/{nurse}/{patient}")
    @ApiOperation("Получение чатов между медперсоналом и пациентами")
    public ResponseEntity<ChatDTO> getChatBetweenNurseAndPatient(@PathVariable("nurse") String nurseEmail, @PathVariable("patient") String patientEmail) {
        log.info("Getting a chat between nurse '{}' and patient '{}'.", nurseEmail, patientEmail);
        Nurse n = nurseService.findByEmail(nurseEmail);
        Patient p = patientService.findByEmail(patientEmail);

        if (n == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Chat c = chatService.findByNurseAndPatient(n, p);
        ChatDTO dto = new ChatDTO(c);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/updateChat/{id}")
    @ApiOperation("Обновление(изменение) отчета по id")
    public ResponseEntity<Void> updateChat(@PathVariable("id") long id, @RequestBody ChatDTO dto) {
        log.info("Update (change) report by id '{}'.", id);
        HttpHeaders header = new HttpHeaders();

        Chat chat = chatService.findById(id);
        if (chat == null) {
            header.set("responseText", "report not found: " + id);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        chat.setDescription(dto.getDescription());
        chat.setName(dto.getName());
        chat.setMessage(dto.getMessage());
        chat.setDateAndTime(dto.getDateAndTime());
        chatService.save(chat);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = "/removeChat1/{doctor}/{patient}")
    @ApiOperation("Удаление чатов между докторами и пациентами")
    public ResponseEntity<Void> removeChatBetweenDoctorAndPatient(@PathVariable("doctor") String doctorEmail, @PathVariable("patient") String patientEmail) {
        log.info("Deleting a chat between doctor '{}' and patient '{}'.", doctorEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();
        Doctor d = doctorService.findByEmail(doctorEmail);
        Patient p = patientService.findByEmail(patientEmail);

        if (d == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Chat c = chatService.findByDoctorAndPatient(d, p);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        c.setDeleted(true);
        chatService.save(c);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeChat2/{nurse}/{patient}")
    @ApiOperation("Удаление чатов между медперсоналом и пациентами")
    public ResponseEntity<Void> removeChatBetweenNurseAndPatient(@PathVariable("nurse") String nurseEmail, @PathVariable("patient") String patientEmail) {
        log.info("Deleting a chat between nurse '{}' and patient '{}'.", nurseEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();
        Nurse n = nurseService.findByEmail(nurseEmail);
        Patient p = patientService.findByEmail(patientEmail);

        if (n == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Chat c = chatService.findByNurseAndPatient(n, p);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        c.setDeleted(true);
        chatService.save(c);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}