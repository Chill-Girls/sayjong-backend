package com.sayjong.backend.song.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayjong.backend.song.domain.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
