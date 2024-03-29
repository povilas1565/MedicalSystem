package service;

import model.Centre;
import model.Doctor;
import model.Nurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.NurseRepository;
import repository.UserRepository;

import java.util.List;


@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private UserRepository userRepository;

    public Nurse findByEmail(String email) {
        return nurseRepository.findByEmail(email);
    }

    public List<Nurse> findByType(String type) {
        return nurseRepository.findByType(type);
    }

    public List<Nurse> findAllByCentreAndType(Centre centre, String type) {
        return nurseRepository.findAllByCentreAndType(centre, type);
    }

    public void save(Nurse nurse) {
        nurseRepository.save(nurse);

    }
}
