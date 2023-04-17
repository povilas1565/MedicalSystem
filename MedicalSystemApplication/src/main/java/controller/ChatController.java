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
    public ResponseEntity<ChatDTO> addChat(@PathVariable("nurse") String nurseEmail, @PathVariable("doctor") String doctorEmail, @PathVariable("patient") String patientEmail, @RequestBody ChatDTO dto) {
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
        HttpHeaders header = new HttpHeaders();
        Doctor doctor = doctorService.findByEmail(doctorEmail);
        Patient patient = patientService.findByEmail(patientEmail);

        if (doctor == null) {
            header.set("responseText", "doctor not found: " + doctorEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = chatService.findByDoctorAndPatient(doctor, patient);
        ChatDTO chatDTO = ChatDTO.builder()
                .id(chat.getId())
                .description(chat.getDescription())
                .name(chat.getName())
                .message(chat.getMessage())
                .dateAndTime(chat.getDateAndTime())
                .doctorEmail(chat.getDoctor().getEmail())
                .patientEmail(chat.getPatient().getEmail())
                .build();

        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getChat2/{nurse}/{patient}")
    @ApiOperation("Получение чатов между медперсоналом и пациентами")
    public ResponseEntity<ChatDTO> getChatBetweenNurseAndPatient(@PathVariable("nurse") String nurseEmail, @PathVariable("patient") String patientEmail) {
        log.info("Getting a chat between nurse '{}' and patient '{}'.", nurseEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();
        Nurse nurse = nurseService.findByEmail(nurseEmail);
        Patient patient = patientService.findByEmail(patientEmail);

        if (nurse == null) {
            header.set("responseText", "nurse not found: " + nurseEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = chatService.findByNurseAndPatient(nurse, patient);
        ChatDTO chatDTO = ChatDTO.builder()
                .id(chat.getId())
                .description(chat.getDescription())
                .name(chat.getName())
                .message(chat.getMessage())
                .dateAndTime(chat.getDateAndTime())
                .nurseEmail(chat.getNurse().getEmail())
                .patientEmail(chat.getPatient().getEmail())
                .build();

        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
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
        Doctor doctor = doctorService.findByEmail(doctorEmail);
        Patient patient = patientService.findByEmail(patientEmail);

        if (doctor == null) {
            header.set("responseText", "doctor not found: " + doctorEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = chatService.findByDoctorAndPatient(doctor, patient);

        if (chat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        chat.setDeleted(true);
        chatService.save(chat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeChat2/{nurse}/{patient}")
    @ApiOperation("Удаление чатов между медперсоналом и пациентами")
    public ResponseEntity<Void> removeChatBetweenNurseAndPatient(@PathVariable("nurse") String nurseEmail, @PathVariable("patient") String patientEmail) {
        log.info("Deleting a chat between nurse '{}' and patient '{}'.", nurseEmail, patientEmail);
        HttpHeaders header = new HttpHeaders();
        Nurse nurse = nurseService.findByEmail(nurseEmail);
        Patient patient = patientService.findByEmail(patientEmail);

        if (nurse == null) {
            header.set("responseText", "nurse not found: " + nurseEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        if (patient == null) {
            header.set("responseText", "patient not found: " + patientEmail);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Chat chat = chatService.findByNurseAndPatient(nurse, patient);


        if (chat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        chat.setDeleted(true);
        chatService.save(chat);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}






