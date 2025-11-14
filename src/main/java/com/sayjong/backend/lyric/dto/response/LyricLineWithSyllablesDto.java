package com.sayjong.backend.lyric.dto.response;

import com.sayjong.backend.lyric.domain.LyricLine;
import com.sayjong.backend.lyric.domain.LyricSyllable;
import com.sayjong.backend.lyric.service.LyricLineService;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LyricLineWithSyllablesDto {
    private final Long lyricLineId;
    private final Integer lineNo;
    private final String originalText;

    // 음절 리스트 포함
    private final List<LyricSyllableResponseDto> syllables;

    @Builder
    public LyricLineWithSyllablesDto(Long lyricLineId, Integer lineNo, String originalText,
                                     String textRomaja, String textEng, String nativeAudioUrl,
                                     Long startTime, String syllableTimings,
                                     List<LyricSyllableResponseDto> syllables) {
        this.lyricLineId = lyricLineId;
        this.lineNo = lineNo;
        this.originalText = originalText;
        this.syllables = syllables;
    }

    public static LyricLineWithSyllablesDto from(LyricLine line, List<LyricSyllable> syllables) {

        // 한국어 가사만 나오도록 필터링
        String cleanedText = LyricLineService.cleanKoreanText(line.getOriginalText());

        List<LyricSyllableResponseDto> syllableDtos = syllables.stream()
                .map(LyricSyllableResponseDto::from)
                .collect(Collectors.toList());

        return LyricLineWithSyllablesDto.builder()
                .lyricLineId(line.getLyricLineId())
                .lineNo(line.getLineNo())
                .originalText(cleanedText)
                .syllables(syllableDtos)
                .build();
    }
}