package com.sayjong.backend.lyric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sayjong.backend.lyric.domain.LyricSyllable;

import java.util.Optional;

@Repository
public interface LyricSyllableRepository
        extends JpaRepository<LyricSyllable, Long> {

    // 노래의 특정 소절에 속한 특정 음절 조회
    Optional<LyricSyllable> findByLyricLineSongSongIdAndLyricLineLineNoAndSylNo(
            Integer songId, Integer lineNo, Integer sylNo
    );
}
