package com.sayjong.backend.training.controller;

import com.sayjong.backend.training.dto.response.ScoreHistoryResponseDto;
import com.sayjong.backend.training.service.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class ScoreHistoryController {

    private final ScoreHistoryService scoreHistoryService;

    // 특정 노래에 대한 점수 기록 조회
    @GetMapping("/session/{sessionId}/scoreHistory")
    public ResponseEntity<List<ScoreHistoryResponseDto>> getMyScoreHistoryForSession(
            @PathVariable Integer sessionId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();

        List<ScoreHistoryResponseDto> historyList = scoreHistoryService
                .getScoreHistoryForSessionByUsername(username, sessionId);

        return ResponseEntity.ok(historyList);
    }
}