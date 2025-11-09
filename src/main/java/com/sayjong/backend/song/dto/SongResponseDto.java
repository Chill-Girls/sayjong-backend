package com.sayjong.backend.song.dto;

import com.sayjong.backend.song.domain.Song;

public record SongResponseDto(
        Integer songId,
        String title,
        String singer,
        String trackId,
        String coverUrl,
        String titleEng,
        String songUrl,
        String timings) {
    public static SongResponseDto fromEntity(Song song) {
        return new SongResponseDto(
                song.getSongId(),
                song.getTitle(),
                song.getSinger(),
                song.getTrackId(),
                song.getCoverUrl(),
                song.getTitleEng(),
                song.getSongUrl(),
                song.getTimings());
    }
}
