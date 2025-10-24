package com.sayjong.backend.song.controller;

import com.sayjong.backend.song.dto.SongResponseDto;
import com.sayjong.backend.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    //노래 목록 조회
    @GetMapping
    public ResponseEntity<List<SongResponseDto>> getAllSongs() {
        List<SongResponseDto> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }
}
