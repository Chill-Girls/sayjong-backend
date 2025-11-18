package com.sayjong.backend.training.service;

import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.song.repository.SongRepository;
import com.sayjong.backend.training.domain.ScoreHistory;
import com.sayjong.backend.training.domain.TrainingSession;
import com.sayjong.backend.training.dto.request.ScoreHistoryRequestDto;
import com.sayjong.backend.training.dto.response.ScoreHistoryResponseDto;
import com.sayjong.backend.training.dto.response.ScoreResponseDto;
import com.sayjong.backend.training.repository.ScoreHistoryRepository;
import com.sayjong.backend.training.repository.TrainingSessionRepository;
import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreHistoryService {

    private final UserRepository userRepository;
    private final ScoreHistoryRepository scoreHistoryRepository;
    private final SongRepository songRepository;
    private final TrainingSessionRepository trainingSessionRepository;

    // 사용자의 노래 별 점수 조회
    public List<ScoreHistoryResponseDto> getScoreHistoryForSessionByUsername(String username, Integer sessionId) {

        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        List<ScoreHistory> histories = scoreHistoryRepository
                .findByUserUserIdAndSessionSessionIdOrderByScoredAtDesc(user.getUserId(), sessionId);

        return histories.stream()
                .map(ScoreHistoryResponseDto::from)
                .collect(Collectors.toList());
    }

    // 노래 점수 저장
    @Transactional
    public ScoreResponseDto saveScore(String username, Integer songId, ScoreHistoryRequestDto.CreateScoreRequest request) {

        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("Song not found"));

        // TrainingSession 조회 또는 생성
        TrainingSession tempSession = trainingSessionRepository.findByUserAndSong(user, song)
                .orElseGet(() -> TrainingSession.builder()
                        .user(user)
                        .song(song)
                        .bestScore(0)
                        .recentScore(0)
                        .averageScore(0)
                        .build());

        TrainingSession managedSession = trainingSessionRepository.save(tempSession);

        // ScoreHistory 생성
        ScoreHistory newScoreHistory = ScoreHistory.builder()
                .score(request.getScore())
                .scoredAt(LocalDateTime.now())
                .user(user)
                .song(song)
                .session(managedSession) 
                .build();

        ScoreHistory savedHistory = scoreHistoryRepository.save(newScoreHistory);

        // 평균 점수 계산
        Double average = scoreHistoryRepository.findAverageScoreBySessionId(managedSession.getSessionId());
        int intAverage = (int) Math.round(average); // 반올림하여 정수로 변환

        // 세션 정보 업데이트 (최근점수, 평균점수, 최고점수)
        managedSession.updateScores(request.getScore(), intAverage);

        return ScoreResponseDto.from(savedHistory);
    }
}