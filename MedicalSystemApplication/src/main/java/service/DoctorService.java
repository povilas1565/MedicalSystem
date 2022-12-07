package service;

import model.Centre;
import model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DoctorRepository;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public List<Doctor> findByType(String type) {
        return doctorRepository.findByType(type);
    }

    public List<Doctor> findAllByCentreAndType(Centre centre, String type) {
        return doctorRepository.findAllByCentreAndType(centre, type);
    }
}
