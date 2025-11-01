package com.sayjong.backend.song.dto;

import com.sayjong.backend.lyric.dto.response.LyricLineResponseDto;

import java.util.List;

public record SongLyricResponseDto(
        Integer songId,
        String title,
        String singer,
        List<LyricLineResponseDto> lyrics
) {
}