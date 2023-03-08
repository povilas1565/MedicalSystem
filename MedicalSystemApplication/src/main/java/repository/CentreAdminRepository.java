package repository;

import model.CentreAdmin;
import model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentreAdminRepository extends JpaRepository<CentreAdmin,Long> {

    CentreAdmin findByEmail(String email);
}

