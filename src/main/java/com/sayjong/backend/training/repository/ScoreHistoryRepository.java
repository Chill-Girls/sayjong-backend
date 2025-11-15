package com.sayjong.backend.training.repository;

import com.sayjong.backend.training.domain.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Integer> {

    // 사용자의 특정 세션에 대한 모든 점수 기록을 조회
    List<ScoreHistory> findByUserUserIdAndSessionSessionIdOrderByScoredAtDesc(Integer userId, Integer sessionId);
}