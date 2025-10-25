package com.sayjong.backend.lyric.domain;

import com.sayjong.backend.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
        name = "lyric_line",
		// DB에서 (song_id, line_no) 조합이 중복되지 않도록 유니크 제약조건 설정
		uniqueConstraints = {@UniqueConstraint(columnNames = {"song_id", "line_no"})}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LyricLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lyric_line_id")
	private Long lyricLineId;

    @Column(name = "line_no", nullable = false)
    private Integer lineNo;  //소절번호 (a곡의 n번째 소절)

	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String originalText;  //원본 가사 (어떤 언어든 저장)

    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String textRomaja; //로마자표기 (영어 가사이면 그냥 원본 가사 유지)

    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String textEng; //영어해석 (영어 가사이면 그냥 원본 가사 유지)

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String nativeAudioUrl; //원어민 오디오 URL (영어여도 오디오 제공)

	@Column(nullable = false)
	private Long startTime; //가사 시작 시간 (milliseconds)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;  //노래식별자
}
