package repository;

import model.Call;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CallRepository extends JpaRepository<Call, Long> {

        Optional<Call> findCallById(Long id);

        Optional<Call> findCallByIdAndUsers(Long id, User user);

       List<Call> findAllByUsers(User user);

       List<Call> findAll();

    }

