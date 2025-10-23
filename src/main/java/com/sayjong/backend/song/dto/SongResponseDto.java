package com.sayjong.backend.song.dto;

import com.sayjong.backend.song.domain.Song;

public record SongResponseDto(
        Integer songId,
        String title,
        String singer,
        String trackId,
        String coverUrl
) {
    public static SongResponseDto fromEntity(Song song) {
        return new SongResponseDto(
                song.getSongId(),
                song.getTitle(),
                song.getSinger(),
                song.getTrackId(),
                song.getCoverUrl()
        );
    }
}
