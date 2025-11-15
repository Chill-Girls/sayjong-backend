package com.sayjong.backend.training.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sayjong.backend.training.domain.ScoreHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScoreHistoryResponseDto {

    private Integer id;
    private Integer score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scoredAt;

    private Integer userId;
    private Integer songId;
    private Integer sessionId;

    public static ScoreHistoryResponseDto from(ScoreHistory history) {
        return ScoreHistoryResponseDto.builder()
                .id(history.getId())
                .score(history.getScore())
                .scoredAt(history.getScoredAt())
                .userId(history.getUser().getUserId())
                .songId(history.getSong().getSongId())
                .sessionId(history.getSession().getSessionId())
                .build();
    }
}