package com.sayjong.backend.lyric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sayjong.backend.lyric.domain.LyricLine;

@Repository
public interface LyricLineRepository extends JpaRepository<LyricLine, Long> {
	boolean existsBySong_SongId(Integer songId);
}
