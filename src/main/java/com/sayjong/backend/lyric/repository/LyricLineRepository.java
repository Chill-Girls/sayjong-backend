package com.sayjong.backend.lyric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sayjong.backend.lyric.domain.LyricLine;

import java.util.List;
import java.util.Optional;

@Repository
public interface LyricLineRepository extends JpaRepository<LyricLine, Long> {
    boolean existsBySong_SongId(Integer songId);

    // 노래 전체 소절 조회
    @Query("SELECT ll FROM LyricLine ll JOIN FETCH ll.song s WHERE s.songId = :songId ORDER BY ll.lineNo ASC")
    List<LyricLine> findAllBySongSongIdOrderByLineNoAsc(@Param("songId") Integer songId);

    // 노래 특정 소절 조회
    Optional<LyricLine> findBySongSongIdAndLineNo(Integer songId, Integer lineNo);
}
