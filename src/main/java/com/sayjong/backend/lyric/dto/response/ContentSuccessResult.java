package com.sayjong.backend.lyric.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * [Node.js -> Spring]
 * Node.js 워커가 가사 생성을 완료한 후 Spring 서버로 반환하는
 * '성공 결과' DTO입니다.
 * * Spring 서버의 WebClient가 이 객체 형태로 JSON 응답을 파싱합니다.
 * 이 객체는 하위에 ProcessedLyricLine 리스트를 포함합니다.
 */
public record ContentSuccessResult(
	@JsonProperty("songId") Integer songId,
	@JsonProperty("title") String title,
	@JsonProperty("titleTranslated") String titleTranslated,
	@JsonProperty("lines") List<ProcessedLyricLine> lines
) {
}
