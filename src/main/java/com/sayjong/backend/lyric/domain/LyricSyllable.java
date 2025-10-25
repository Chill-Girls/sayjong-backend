package com.sayjong.backend.lyric.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
        name = "lyric_syllable",
		// 한 소절(lyric_line_id) 안에서 음절 번호(syl_no)가 중복되지 않도록 설정
		uniqueConstraints = {@UniqueConstraint(columnNames = {"lyric_line_id", "syl_no"})}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LyricSyllable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lyric_syllable_id")
	private Long lyricSyllableId;

	@Column(nullable = false)
    private Integer sylNo; //음절번호 (소절 내 순서)

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
	@JoinColumn(name = "lyric_line_id", nullable = false)
	private LyricLine lyricLine;
}
