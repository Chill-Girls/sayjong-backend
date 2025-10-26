package com.sayjong.backend.lyric.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * 클라이언트가 가사 생성을 요청할 때 보내는 DTO.
 * songId만 포함한다.
 */
public record AppLyricRequest(
	@NotNull(message = "songId는 필수입니다.")
	Integer songId
) {}