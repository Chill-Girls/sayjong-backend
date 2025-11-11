package com.sayjong.backend.lyric.dto.response;

import com.sayjong.backend.lyric.domain.LyricSyllable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LyricSyllableResponseDto {
    private final Integer sylNo;
    private final String textKor;
    private final String textRomaja;
    private final String nativeAudioUrl;

    @Builder
    public LyricSyllableResponseDto(Integer sylNo, String textKor, String textRomaja, String nativeAudioUrl) {
        this.sylNo = sylNo;
        this.textKor = textKor;
        this.textRomaja = textRomaja;
        this.nativeAudioUrl = nativeAudioUrl;
    }

    public static LyricSyllableResponseDto from(LyricSyllable syllable) {
        return LyricSyllableResponseDto.builder()
                .sylNo(syllable.getSylNo())
                .textKor(syllable.getTextKor())
                .textRomaja(syllable.getTextRomaja())
                .nativeAudioUrl(syllable.getNativeAudioUrl())
                .build();
    }
}