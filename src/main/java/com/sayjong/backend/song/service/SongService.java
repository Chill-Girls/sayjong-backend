package com.sayjong.backend.song.service;

import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.song.dto.SongResponseDto;
import com.sayjong.backend.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;

    //노래 목록 조회
    public List<SongResponseDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();

        return songs.stream()
                .map(SongResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
