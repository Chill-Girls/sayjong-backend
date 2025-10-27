package com.sayjong.backend.lyric.service;

import com.sayjong.backend.lyric.domain.LyricLine;
import com.sayjong.backend.lyric.dto.response.LyricLineResponseDto;
import com.sayjong.backend.lyric.repository.LyricLineRepository;
import com.sayjong.backend.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LyricLineService {

    private final LyricLineRepository lyricLineRepository;
    private final SongRepository songRepository;

    public List<LyricLineResponseDto> getLyricLinesBySongId(Integer songId) {
        if (!songRepository.existsById(songId)) {
            throw new EntityNotFoundException("Song not found with id: " + songId);
        }

        List<LyricLine> lyricLines = lyricLineRepository.findAllBySongSongIdOrderByLineNoAsc(songId);

        return lyricLines.stream()
                .map(LyricLineResponseDto::from)
                .collect(Collectors.toList());
    }
}
