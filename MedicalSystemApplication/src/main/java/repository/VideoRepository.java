package repository;

import model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByUserId(Long userId);

    Optional<Video> findByCallId(Long callId);
}
