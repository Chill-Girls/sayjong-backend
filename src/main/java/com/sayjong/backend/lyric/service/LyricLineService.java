package com.sayjong.backend.lyric.service;

import com.sayjong.backend.lyric.domain.LyricLine;
import com.sayjong.backend.lyric.dto.response.LyricLineResponseDto;
import com.sayjong.backend.lyric.repository.LyricLineRepository;
import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.song.dto.SongLyricResponseDto;
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

    // 전체 소절 조회
    public SongLyricResponseDto getLyricLinesBySongId(Integer songId) {
        List<LyricLine> lyricLines = lyricLineRepository.findAllBySongSongIdOrderByLineNoAsc(songId);

        if (lyricLines.isEmpty()) {
            throw new EntityNotFoundException("Song or lyrics not found for ID: " + songId);
        }

        List<LyricLineResponseDto> lyricDtos = lyricLines.stream()
                .map(LyricLineResponseDto::from)
                .toList();
        Song song = lyricLines.get(0).getSong();

        // SongLyricResponseDto를 조립하여 반환
        return new SongLyricResponseDto(
                song.getSongId(),
                song.getTitle(),
                song.getSinger(),
                lyricDtos
        );
    }

    // 특정 소절 조회
    public LyricLineResponseDto getLyricLineBySongIdAndLineNo(Integer songId, Integer lineNo) {
        LyricLine lyricLine = lyricLineRepository.findBySongSongIdAndLineNo(songId, lineNo)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Lyric line not found for songId: " + songId + " and lineNo: " + lineNo
                ));
        return LyricLineResponseDto.from(lyricLine);
    }
}
