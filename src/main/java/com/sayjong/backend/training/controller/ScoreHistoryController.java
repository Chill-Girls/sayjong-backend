package com.sayjong.backend.training.controller;

import com.sayjong.backend.training.dto.request.ScoreHistoryRequestDto;
import com.sayjong.backend.training.dto.response.ScoreHistoryResponseDto;
import com.sayjong.backend.training.dto.response.ScoreResponseDto;
import com.sayjong.backend.training.service.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class ScoreHistoryController {

    private final ScoreHistoryService scoreHistoryService;

    // 노래방 점수 저장
    @PostMapping("/songs/{songId}/scores")
    public ResponseEntity<ScoreResponseDto> createScoreRecord(
            @PathVariable("songId") Integer songId,
            @RequestBody ScoreHistoryRequestDto.CreateScoreRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        ScoreResponseDto response = scoreHistoryService.saveScore(username, songId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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