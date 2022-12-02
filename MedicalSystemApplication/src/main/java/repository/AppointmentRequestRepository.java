package repository;

import model.AppointmentRequest;
import model.Centre;
import model.Hall;
import model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {

    AppointmentRequest findByDateAndHallAndCentre(Date date, Hall hall, Centre centre);

    AppointmentRequest findByDateAndPatientAndCentre(Date date, Patient patient, Centre centre);
    List<AppointmentRequest> findAllByPatient(Patient patient);
    List<AppointmentRequest> findAllByCentre(Centre centre);

    List<AppointmentRequest> findAll();
}
