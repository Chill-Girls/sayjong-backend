package com.sayjong.backend.training.service;

import com.sayjong.backend.training.domain.TrainingSession;
import com.sayjong.backend.training.dto.response.TrainingSessionResponseDto;
import com.sayjong.backend.training.repository.TrainingSessionRepository;
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
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final UserRepository userRepository;

    public List<TrainingSessionResponseDto> getMySessions(String username) {
        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 전체 조회 (최신순)
        List<TrainingSession> sessions = trainingSessionRepository.findAllByUserOrderByLastPlayedAtDesc(user);

        return sessions.stream()
                .map(TrainingSessionResponseDto::from)
                .collect(Collectors.toList());
    }
}