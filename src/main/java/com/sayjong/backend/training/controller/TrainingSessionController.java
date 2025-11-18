package com.sayjong.backend.training.controller;

import com.sayjong.backend.training.dto.response.TrainingSessionResponseDto;
import com.sayjong.backend.training.service.TrainingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/me/training-sessions")
@RequiredArgsConstructor
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    // 학습 내역 조회
    @GetMapping
    public ResponseEntity<List<TrainingSessionResponseDto>> getMySessions(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();

        List<TrainingSessionResponseDto> response = trainingSessionService.getMySessions(username);

        return ResponseEntity.ok(response);
    }
}