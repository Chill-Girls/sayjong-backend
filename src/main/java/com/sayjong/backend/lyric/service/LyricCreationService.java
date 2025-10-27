package com.sayjong.backend.lyric.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sayjong.backend.lyric.domain.LyricLine;
import com.sayjong.backend.lyric.domain.LyricSyllable;
import com.sayjong.backend.lyric.dto.response.ContentSuccessResult;
import com.sayjong.backend.lyric.dto.response.ProcessedLyricLine;
import com.sayjong.backend.lyric.dto.response.ProcessedLyricSyllable;
import com.sayjong.backend.lyric.repository.LyricLineRepository;
import com.sayjong.backend.lyric.repository.LyricSyllableRepository;
import com.sayjong.backend.song.domain.Song;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LyricCreationService {
    private final LyricLineRepository lyricLineRepository;
    private final LyricSyllableRepository lyricSyllableRepository;

    @Transactional
    public void saveLyrics(ContentSuccessResult result, Song song) {
        log.info("[LyricCreation] Saving lyrics for songId: {}",
                result.songId());

        List<LyricLine> linesToSave = new ArrayList<>();
        List<LyricSyllable> syllablesToSave = new ArrayList<>();
        List<ProcessedLyricLine> lineDtos = result.lines();

        for (int i = 0; i < lineDtos.size(); i++) {
            ProcessedLyricLine lineDto = lineDtos.get(i);
            int lineNo = i + 1;

            LyricLine lyricLine = LyricLine.builder()
                    .lineNo(lineNo)
                    .originalText(lineDto.originalText())
                    .textRomaja(lineDto.textRomaja())
                    .textEng(lineDto.textEng())
                    .nativeAudioUrl(lineDto.nativeAudioUrl())
                    .startTime(Long.parseLong(lineDto.startTime()))
                    .song(song)
                    .build();

            linesToSave.add(lyricLine);

            List<ProcessedLyricSyllable> syllableDtos = lineDto.syllables();
            if (syllableDtos != null && !syllableDtos.isEmpty()) {
                for (int j = 0; j < syllableDtos.size(); j++) {
                    ProcessedLyricSyllable syllableDto = syllableDtos.get(j);
                    int sylNo = j + 1;

                    LyricSyllable lyricSyllable = LyricSyllable.builder()
                            .sylNo(sylNo)
                            .textKor(syllableDto.textKor())
                            .textRomaja(syllableDto.textRomaja())
                            .nativeAudioUrl(syllableDto.nativeAudioUrl())
                            .lyricLine(lyricLine)
                            .build();

                    syllablesToSave.add(lyricSyllable);
                }
            }
        }

        lyricLineRepository.saveAll(linesToSave);
        lyricSyllableRepository.saveAll(syllablesToSave);

        log.info(
                "[LyricCreation] Successfully saved {} lines and {} syllables for songId: {}",
                linesToSave.size(), syllablesToSave.size(), song.getSongId());
    }
}
