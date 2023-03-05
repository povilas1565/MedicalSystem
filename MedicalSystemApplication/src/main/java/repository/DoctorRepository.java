package repository;

import model.Centre;
import model.Doctor;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    public Doctor findByEmail(String email);

    public List<Doctor> findByType(String type);

    public List<Doctor> findAllByCentreAndType(Centre centre, String type);


    public List<User> findAllByRole(User.UserRole role);

}
