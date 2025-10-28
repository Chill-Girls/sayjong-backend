package com.sayjong.backend.lyric.service;

import com.sayjong.backend.lyric.domain.LyricSyllable;
import com.sayjong.backend.lyric.dto.response.LyricSyllableResponseDto;
import com.sayjong.backend.lyric.repository.LyricSyllableRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LyricSyllableService {

    private final LyricSyllableRepository lyricSyllableRepository;

    public LyricSyllableResponseDto getSpecificSyllable(Integer songId, Integer lineNo, Integer sylNo) {

        LyricSyllable syllable = lyricSyllableRepository
                .findByLyricLineSongSongIdAndLyricLineLineNoAndSylNo(songId, lineNo, sylNo)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Syllable not found for songId: " + songId + ", lineNo: " + lineNo + ", sylNo: " + sylNo
                ));
        return LyricSyllableResponseDto.from(syllable);
    }
}
