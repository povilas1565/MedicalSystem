package service;
import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PatientRepository;
import repository.UserRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public void save(Patient patient) {
        patientRepository.save(patient);
    }
}
