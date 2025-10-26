package com.sayjong.backend.lyric.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * [Spring -> Node.js]
 * Spring 서버(WebClient)가 Node.js 워커 서버의
 * /create-content API를 호출할 때 사용하는 요청 DTO입니다.
 * Node.js 서버가 필요로 하는 모든 정보(songId, trackId, title)를 담습니다.
 */
public record WorkerLyricRequest(
	@JsonProperty("songId") Integer songId,
	@JsonProperty("trackId") String trackId,
	@JsonProperty("title") String title
) {
}
