package repository;

import model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AudioRepository extends JpaRepository<Audio, Long> {

        Optional<Audio> findByUserId(Long userId);

        Optional<Audio> findByCallId(Long callId);
}
