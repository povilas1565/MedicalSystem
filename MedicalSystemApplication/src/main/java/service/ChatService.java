package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ChatRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public void delete(Chat chat) {
       chatRepository.delete(chat);
    }

    public Chat findByNurseAndPatient(Nurse nurse, Patient patient) {
        return chatRepository.findByNurseAndPatient(nurse,patient);
    }

    public Chat findByDoctorAndPatient(Doctor doctor, Patient patient) {
        return chatRepository.findByDoctorAndPatient(doctor, patient);
    }


    public Chat findById(long id) {
        return chatRepository.findById(id);
    }
}

