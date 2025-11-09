package com.sayjong.backend.song.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "song")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer songId; // 노래식별자, PK

    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String title; // 노래제목

    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String titleEng; // 노래 영문 제목

    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String singer; // 가수

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String trackId; // Spotify 트랙 ID

    @Lob
    @Column(columnDefinition = "TEXT")
    private String coverUrl; // 커버이미지 URL

    @Lob
    @Column(columnDefinition = "TEXT")
    private String songUrl; // 노래 URL

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String timings; // 타이밍 (JSON)
}
