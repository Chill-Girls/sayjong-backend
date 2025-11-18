package com.sayjong.backend.training.dto.response;

import com.sayjong.backend.training.domain.ScoreHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScoreResponseDto {
    private Integer id;
    private Integer score;
    private LocalDateTime scoredAt;

    public static ScoreResponseDto from(ScoreHistory entity) {
        return ScoreResponseDto.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .scoredAt(entity.getScoredAt())
                .build();
    }
}