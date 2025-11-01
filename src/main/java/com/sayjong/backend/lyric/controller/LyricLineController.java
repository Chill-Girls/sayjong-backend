package com.sayjong.backend.lyric.controller;

import com.sayjong.backend.lyric.dto.response.LyricLineResponseDto;
import com.sayjong.backend.lyric.dto.response.LyricSyllableResponseDto;
import com.sayjong.backend.lyric.service.LyricLineService;
import com.sayjong.backend.lyric.service.LyricSyllableService;
import com.sayjong.backend.song.dto.SongLyricResponseDto;
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

    // 특정 노래의 소절별 음절 정보 가져오기
    @GetMapping("/{songId}/lyriclines/{lineNo}/syllables/{sylNo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LyricSyllableResponseDto> getSpecificSyllable(
            @PathVariable("songId") Integer songId,
            @PathVariable("lineNo") Integer lineNo,
            @PathVariable("sylNo") Integer sylNo
    ) {
        LyricSyllableResponseDto syllableDto = lyricSyllableService
                .getSpecificSyllable(songId, lineNo, sylNo);

        return ResponseEntity.ok(syllableDto);
    }
}
