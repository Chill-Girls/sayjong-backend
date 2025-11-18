package com.sayjong.backend.training.repository;

import com.sayjong.backend.training.domain.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Integer> {

    // 사용자의 특정 세션에 대한 모든 점수 기록을 조회
    List<ScoreHistory> findByUserUserIdAndSessionSessionIdOrderByScoredAtDesc(Integer userId, Integer sessionId);

    // 특정 세션의 점수 평균을 계산
    @Query("SELECT COALESCE(AVG(s.score), 0) FROM ScoreHistory s WHERE s.session.sessionId = :sessionId")
    Double findAverageScoreBySessionId(@Param("sessionId") Integer sessionId);
}