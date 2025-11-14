package com.sayjong.backend.lyric.dto.response;

import com.sayjong.backend.lyric.domain.LyricLine;
import com.sayjong.backend.lyric.service.LyricLineService;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LyricLineResponseDto {
    private final Long lyricLineId;
    private final Integer lineNo;
    private final String originalText;
    private final String textRomaja;
    private final String textEng;
    private String nativeAudioUrl;
    private final Long startTime;
    private String syllableTimings;

    @Builder
    public LyricLineResponseDto(Long lyricLineId, Integer lineNo,
            String originalText, String textRomaja, String textEng,
            String nativeAudioUrl, Long startTime, String syllableTimings) {
        this.lyricLineId = lyricLineId;
        this.lineNo = lineNo;
        this.originalText = originalText;
        this.textRomaja = textRomaja;
        this.textEng = textEng;
        this.nativeAudioUrl = nativeAudioUrl;
        this.startTime = startTime;
        this.syllableTimings = syllableTimings;
    }

    public static LyricLineResponseDto from(LyricLine lyricLine) {

        // 한국어 가사만 나오도록 필터링
        String cleanedText = LyricLineService.cleanKoreanText(lyricLine.getOriginalText());

        return LyricLineResponseDto.builder()
                .lyricLineId(lyricLine.getLyricLineId())
                .lineNo(lyricLine.getLineNo())
                .originalText(cleanedText)
                .textRomaja(lyricLine.getTextRomaja())
                .textEng(lyricLine.getTextEng())
                .nativeAudioUrl(lyricLine.getNativeAudioUrl())
                .startTime(lyricLine.getStartTime())
                .syllableTimings(lyricLine.getSyllableTimings())
                .build();
    }
}
