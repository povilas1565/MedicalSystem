package repository;

import model.Centre;
import model.Doctor;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByEmail(String email);

    List<Doctor> findByType(String type);

    List<Doctor> findAllByCentreAndType(Centre centre, String type);


     List<User> findAllByRole(User.UserRole role);

}
