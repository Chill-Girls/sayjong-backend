package com.sayjong.backend.song.repository;

import com.sayjong.backend.song.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
