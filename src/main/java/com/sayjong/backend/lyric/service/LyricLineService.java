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
import com.sayjong.backend.lyric.domain.LyricSyllable;
import com.sayjong.backend.lyric.repository.LyricSyllableRepository;
import com.sayjong.backend.song.dto.SongLyricsWithSyllablesDto;
import com.sayjong.backend.lyric.dto.response.LyricLineWithSyllablesDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LyricLineService {

    private final LyricLineRepository lyricLineRepository;
    private final SongRepository songRepository;
    private final LyricSyllableRepository lyricSyllableRepository;

    // 문자열에서 한글과 공백을 제외한 모든 문자 제거
    public static String cleanKoreanText(String text) {
        if (text == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= '\uAC00' && c <= '\uD7A3') { // 한글
                sb.append(c);
            } else if (Character.isWhitespace(c)) { // 공백
                sb.append(c);
            }
        }
        return sb.toString().trim();
    }

    // 전체 소절 조회
    public SongLyricResponseDto getLyricLinesBySongId(Integer songId) {
        List<LyricLine> lyricLines = lyricLineRepository.findAllBySongSongIdOrderByLineNoAsc(songId);

        if (lyricLines.isEmpty()) {
            throw new EntityNotFoundException("Song or lyrics not found for ID: " + songId);
        }

        Song song = lyricLines.get(0).getSong();

        List<LyricLineResponseDto> lyricDtos = lyricLines.stream()
                .map(LyricLineResponseDto::from)
                .filter(dto -> !dto.getOriginalText().isBlank())
                .toList();

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


    // 전체 소절 + 전체 음절 조회
    public SongLyricsWithSyllablesDto getSongLyricsWithSyllables(Integer songId) {

        // 노래 정보 조회
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("Song not found for ID: " + songId));

        // 노래에 속한 모든 소절(Line) 조회
        List<LyricLine> lines = lyricLineRepository.findAllBySongSongIdOrderByLineNoAsc(songId);

        if (lines.isEmpty()) {
            return SongLyricsWithSyllablesDto.from(song, Collections.emptyList());
        }

        List<Long> lineIds = lines.stream()
                .map(LyricLine::getLyricLineId)
                .collect(Collectors.toList());

        List<LyricSyllable> allSyllables = lyricSyllableRepository.findAllByLyricLineIds(lineIds);

        Map<Long, List<LyricSyllable>> syllablesByLineIdMap = allSyllables.stream()
                .collect(Collectors.groupingBy(s -> s.getLyricLine().getLyricLineId()));

        List<LyricLineWithSyllablesDto> lineDtos = lines.stream()
                .filter(line -> syllablesByLineIdMap.containsKey(line.getLyricLineId()))
                .map(line -> {
                    List<LyricSyllable> syllablesForThisLine = syllablesByLineIdMap.get(line.getLyricLineId());
                    return LyricLineWithSyllablesDto.from(line, syllablesForThisLine);
                })
                .filter(dto -> !dto.getOriginalText().isBlank())
                .collect(Collectors.toList());

        // 최종 응답 DTO ('SongLyricsWithSyllablesDto') 생성
        return SongLyricsWithSyllablesDto.from(song, lineDtos);
    }

}
