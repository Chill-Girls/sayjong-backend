package com.sayjong.backend.lyric.dto.response;

import com.sayjong.backend.lyric.domain.LyricLine;
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

    @Builder
    public LyricLineResponseDto(Long lyricLineId, Integer lineNo, String originalText, String textRomaja, String textEng, String nativeAudioUrl, Long startTime) {
        this.lyricLineId = lyricLineId;
        this.lineNo = lineNo;
        this.originalText = originalText;
        this.textRomaja = textRomaja;
        this.textEng = textEng;
        this.nativeAudioUrl = nativeAudioUrl;
        this.startTime = startTime;
    }

    public static LyricLineResponseDto from(LyricLine lyricLine) {
        return LyricLineResponseDto.builder()
                .lyricLineId(lyricLine.getLyricLineId())
                .lineNo(lyricLine.getLineNo())
                .originalText(lyricLine.getOriginalText())
                .textRomaja(lyricLine.getTextRomaja())
                .textEng(lyricLine.getTextEng())
                .nativeAudioUrl(lyricLine.getNativeAudioUrl())
                .startTime(lyricLine.getStartTime())
                .build();
    }
}
