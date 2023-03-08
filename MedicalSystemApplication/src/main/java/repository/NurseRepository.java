package repository;

import model.Centre;
import model.Doctor;
import model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NurseRepository extends JpaRepository<Nurse,Long> {

        Nurse findByEmail(String email);

        List<Nurse> findByType(String type);

        List<Nurse> findAllByCentreAndType(Centre centre, String type);

}
