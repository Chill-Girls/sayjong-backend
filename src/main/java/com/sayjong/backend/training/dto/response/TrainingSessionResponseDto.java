package com.sayjong.backend.training.dto.response;

import com.sayjong.backend.training.domain.TrainingSession;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TrainingSessionResponseDto {
    private Integer sessionId;
    private Integer averageScore;
    private Integer bestScore;
    private Integer recentScore;
    private LocalDateTime lastPlayedAt;

    // 노래 정보
    private Integer songId;
    private String title;
    private String singer;
    private String coverUrl;

    public static TrainingSessionResponseDto from(TrainingSession session) {
        return TrainingSessionResponseDto.builder()
                .sessionId(session.getSessionId())
                .averageScore(session.getAverageScore())
                .bestScore(session.getBestScore())
                .recentScore(session.getRecentScore())
                .lastPlayedAt(session.getLastPlayedAt())
                .songId(session.getSong().getSongId())
                .title(session.getSong().getTitle())
                .singer(session.getSong().getSinger())
                .coverUrl(session.getSong().getCoverUrl())
                .build();
    }
}