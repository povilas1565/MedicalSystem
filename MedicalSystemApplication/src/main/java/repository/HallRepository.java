package repository;

import model.Centre;
import model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {

    public Hall findByNumberAndCentreAndDeleted(int number, Centre centre, Boolean deleted);

    public List<Hall> findByCentre(Centre c);
}
