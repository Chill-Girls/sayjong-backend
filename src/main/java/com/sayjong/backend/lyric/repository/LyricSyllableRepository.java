package com.sayjong.backend.lyric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sayjong.backend.lyric.domain.LyricSyllable;

import java.util.List;
import java.util.Optional;

@Repository
public interface LyricSyllableRepository
        extends JpaRepository<LyricSyllable, Long> {

    // 노래의 특정 소절에 속한 특정 음절 조회
    Optional<LyricSyllable> findByLyricLineSongSongIdAndLyricLineLineNoAndSylNo(
            Integer songId, Integer lineNo, Integer sylNo
    );

    // 소절에 속한 모든 음절을 한 번에 조회
    @Query("SELECT s FROM LyricSyllable s " +
            "WHERE s.lyricLine.lyricLineId IN :lineIds " +
            "ORDER BY s.sylNo ASC")
    List<LyricSyllable> findAllByLyricLineIds(@Param("lineIds") List<Long> lineIds);
}
