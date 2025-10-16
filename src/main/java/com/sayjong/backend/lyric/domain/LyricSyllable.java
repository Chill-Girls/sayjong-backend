package com.sayjong.backend.lyric.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
        name = "lyric_syllable",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"syl_no", "line_no"})}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LyricSyllable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer sylNo; //음절번호(PK)

    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String textKor; //한국어소절

    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String textRomaja; //로마자표기

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String nativeAudioUrl; //원어민(한국어발음)오디오 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_no", nullable = false)
    private LyricLine lyricLine; //소절번호
}
