package com.sayjong.backend.lyric.controller;

import com.sayjong.backend.lyric.dto.response.LyricLineResponseDto;
import com.sayjong.backend.lyric.service.LyricLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class LyricLineController {

    private final LyricLineService lyricLineService;

    // 특정 노래의 전체 소절 정보 조회
    @GetMapping("/{songId}/lyriclines")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LyricLineResponseDto>> getLyricLinesBySong(
            @PathVariable("songId") Integer songId
    ) {
        List<LyricLineResponseDto> lyricLines = lyricLineService.getLyricLinesBySongId(songId);
        return ResponseEntity.ok(lyricLines);
    }
}
