package service;

import model.Centre;
import model.Doctor;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.DoctorRepository;
import repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public List<Doctor> findByType(String type) {
        return doctorRepository.findByType(type);
    }

    public List<Doctor> findAllByCentreAndType(Centre centre, String type) {
        return doctorRepository.findAllByCentreAndType(centre, type);
    }

    public void save(Doctor doctor) {
        doctorRepository.save(doctor);

    }

}
