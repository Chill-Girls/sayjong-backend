package com.sayjong.backend.lyric.controller;

import com.sayjong.backend.lyric.dto.response.LyricLineResponseDto;
import com.sayjong.backend.lyric.dto.response.LyricSyllableResponseDto;
import com.sayjong.backend.lyric.service.LyricLineService;
import com.sayjong.backend.lyric.service.LyricSyllableService;
import com.sayjong.backend.song.dto.SongLyricResponseDto;
import com.sayjong.backend.song.dto.SongLyricsWithSyllablesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class LyricLineController {

    private final LyricLineService lyricLineService;
    private final LyricSyllableService lyricSyllableService;

    // 특정 노래의 전체 소절 정보 조회
    @GetMapping("/{songId}/lyriclines")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SongLyricResponseDto> getLyricLinesBySong(
            @PathVariable Integer songId
    ) {
        SongLyricResponseDto response = lyricLineService.getLyricLinesBySongId(songId);
        return ResponseEntity.ok(response);
    }

    // 특정 노래의 특정 소절 정보 조회
    @GetMapping("/{songId}/lyriclines/{lineNo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LyricLineResponseDto> getSpecificLyricLine(
            @PathVariable("songId") Integer songId,
            @PathVariable("lineNo") Integer lineNo
    ) {
        LyricLineResponseDto lyricLineDto = lyricLineService.getLyricLineBySongIdAndLineNo(songId, lineNo);
        return ResponseEntity.ok(lyricLineDto);
    }

    // 노래별 전체 음절 정보
    @GetMapping("/{songId}/lyricSyllables")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SongLyricsWithSyllablesDto> getSongLyricsWithSyllables(
            @PathVariable Integer songId
    ) {
        SongLyricsWithSyllablesDto response = lyricLineService.getSongLyricsWithSyllables(songId);
        return ResponseEntity.ok(response);
    }
}
