package repository;

import model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findByPatient(Patient patient);

    Chat findByDoctor(Doctor doctor);

    Chat findByNurse(Nurse nurse);

    Chat findById(long id);
}

