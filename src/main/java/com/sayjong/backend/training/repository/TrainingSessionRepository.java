package com.sayjong.backend.training.repository;

import com.sayjong.backend.training.domain.TrainingSession;
import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer> {

    Optional<TrainingSession> findByUserAndSong(User user, Song song);
}