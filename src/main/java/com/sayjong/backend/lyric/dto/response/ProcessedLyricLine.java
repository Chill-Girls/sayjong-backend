package com.sayjong.backend.lyric.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProcessedLyricLine(
	@JsonProperty("startTime") String startTime,
	@JsonProperty("words") String originalText,
	@JsonProperty("romanized") String textRomaja,
	@JsonProperty("translated") String textEng,
	@JsonProperty("nativeAudio") String nativeAudioUrl,
	@JsonProperty("syllables") List<ProcessedLyricSyllable> syllables
) {
}
