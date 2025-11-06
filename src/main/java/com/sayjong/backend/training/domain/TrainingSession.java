package com.sayjong.backend.training.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.user.domain.User;

import lombok.*;

@Entity
@Table(name = "training_session", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "user_id", "song_id" }) } // 한사용자당 한 노래는
                                                                // 1행만 유지
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer sessionId; // 학습목록ID(PK)

    @Column
    private Integer bestScore; // best 점수

    @Column
    private Integer recentScore; // 가장 최근 점수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 유저식별자
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false) // 노래식별자
    private Song song;
}
