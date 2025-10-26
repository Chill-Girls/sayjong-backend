package com.sayjong.backend.lyric.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProcessedLyricSyllable(
	@JsonProperty("textKor") String textKor,
	@JsonProperty("romanized") String textRomaja,
	@JsonProperty("nativeAudio") String nativeAudioUrl
) {
}
