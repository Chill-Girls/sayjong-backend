package com.sayjong.backend.song.dto;

import com.sayjong.backend.lyric.dto.response.LyricLineWithSyllablesDto;
import com.sayjong.backend.song.domain.Song;

import java.util.List;

public record SongLyricsWithSyllablesDto(
        Integer songId,
        String title,
        String singer,
        List<LyricLineWithSyllablesDto> lyrics
) {

    public static SongLyricsWithSyllablesDto from(Song song, List<LyricLineWithSyllablesDto> lyrics) {
        return new SongLyricsWithSyllablesDto(
                song.getSongId(),
                song.getTitle(),
                song.getSinger(),
                lyrics
        );
    }
}