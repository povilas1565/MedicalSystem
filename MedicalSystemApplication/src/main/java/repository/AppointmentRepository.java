package repository;

import model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    public Appointment findByDateAndHallAndCentre(Date date, Hall hall, Centre centre);

    public List<Appointment> findAllByHallAndCentre(Hall hall, Centre centre);

    public List<Appointment> findAllByPatient(Patient p);

    @Query(value = "select * from appointment where id = any(select appointments_id from doctor_appointments where doctor_id=?1)",nativeQuery = true)
    public List<Appointment> findAllByDoctor(Long doctorID);

    public List<Appointment> findAllByHall(Hall hall);

    public List<Appointment> findAllByCentre(Centre c);

    public List<Appointment> findAllByPredefined(Boolean predefined);

    public List<Appointment> findAllByDate(Date date);

    public List<Appointment> findAllByPriceslist(Priceslist priceslist);
}
