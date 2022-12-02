package repository;

import model.Centre;
import model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface CentreRepository extends JpaRepository<Centre, Long> {

    public Centre findByName(String name);

    public Centre findByDoctors(Doctor d);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    public List<Centre> findAll();
}
