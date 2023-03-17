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

    public Chat findByPatient(Patient patient) {
        return chatRepository.findByPatient(patient);
    }

    public Chat findByDoctor(Doctor doctor) {
        return chatRepository.findByDoctor(doctor);
    }

    public Chat findByNurse(Nurse nurse) {
        return chatRepository.findByNurse(nurse);
    }

    public Chat findById(long id) {
        return chatRepository.findById(id);
    }
}

