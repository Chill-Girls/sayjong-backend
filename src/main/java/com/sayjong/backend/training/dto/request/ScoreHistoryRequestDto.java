package com.sayjong.backend.training.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class ScoreHistoryRequestDto {

    @Getter
    @NoArgsConstructor
    public static class CreateScoreRequest {
        private Integer score;
    }
}