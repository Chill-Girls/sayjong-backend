package com.sayjong.backend.training.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.user.domain.User;

import lombok.*;

@Entity
@Table(name = "score_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScoreHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id; // 점수기록ID(PK)

    @Column(nullable = false)
    private Integer score; // 점수

    @Column(nullable = false)
    private LocalDateTime scoredAt; // 학습날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 유저식별자
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false) // 노래식별자
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false) // 학습목록ID
    private TrainingSession session;

}
