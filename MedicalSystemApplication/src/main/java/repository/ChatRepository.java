package repository;

import model.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findByDoctor(Doctor doctor);

    Chat findByPatient(Patient patient);

    Chat findByNurse(Nurse nurse);

    Chat findByDoctorAndPatient(Doctor doctor, Patient patient);

    Chat findByNurseAndPatient(Nurse nurse, Patient patient);

    Chat findById(long id);
}

