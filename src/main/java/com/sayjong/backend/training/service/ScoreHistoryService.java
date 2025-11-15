package com.sayjong.backend.training.service;

import com.sayjong.backend.training.domain.ScoreHistory;
import com.sayjong.backend.training.dto.response.ScoreHistoryResponseDto;
import com.sayjong.backend.training.repository.ScoreHistoryRepository;
import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreHistoryService {

    private final UserRepository userRepository;
    private final ScoreHistoryRepository scoreHistoryRepository;

    public List<ScoreHistoryResponseDto> getScoreHistoryForSessionByUsername(String username, Integer sessionId) {

        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        List<ScoreHistory> histories = scoreHistoryRepository
                .findByUserUserIdAndSessionSessionIdOrderByScoredAtDesc(user.getUserId(), sessionId);

        return histories.stream()
                .map(ScoreHistoryResponseDto::from)
                .collect(Collectors.toList());
    }
}