package repository;

import model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AudioRepository extends JpaRepository<Audio, Long> {

        Optional<Audio> findById(Long id);

        Optional<Audio> findByCallId(Long callId);
}
