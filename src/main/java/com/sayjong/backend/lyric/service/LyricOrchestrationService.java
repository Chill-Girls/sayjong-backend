package com.sayjong.backend.lyric.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sayjong.backend.lyric.dto.request.AppLyricRequest;
import com.sayjong.backend.lyric.dto.request.WorkerLyricRequest;
import com.sayjong.backend.lyric.dto.response.ContentSuccessResult;
import com.sayjong.backend.lyric.repository.LyricLineRepository;
import com.sayjong.backend.song.domain.Song;
import com.sayjong.backend.song.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LyricOrchestrationService {
    private final WebClient nodeWorkerWebClient;
    private final LyricCreationService lyricCreationService;
    private final LyricLineRepository lyricLineRepository;
    private final SongRepository songRepository;

    public boolean createAndSaveLyrics(AppLyricRequest request) {

        Integer songId = request.songId();

        if (lyricLineRepository.existsBySong_SongId(songId)) {
            log.warn(
                    "[LyricOrchestration] Skipped: Lyrics for songId {} already exist.",
                    songId);
            return false;
        }

        log.debug("[LyricOrchestration] Finding song info for songId: {}",
                songId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Song not found with id: " + songId));

        WorkerLyricRequest nodeRequestPayload = new WorkerLyricRequest(
                song.getSongId(),
                song.getTrackId(),
                song.getTitle());
        log.info(
                "[LyricOrchestration] Requesting lyric creation from worker for: {}",
                nodeRequestPayload);

        ContentSuccessResult result = nodeWorkerWebClient.post()
                .uri("/create-content")
                .bodyValue(nodeRequestPayload)
                .retrieve()
                .bodyToMono(ContentSuccessResult.class)
                .block();

        if (result == null) {
            log.error(
                    "[LyricOrchestration] Failed to get valid result from worker for songId: {}",
                    songId);
            throw new IllegalStateException(
                    "Failed to get valid result from worker.");
        }

        lyricCreationService.saveLyrics(result, song);

        log.info("[LyricOrchestration] Process completed for songId: {}",
                songId);
        return true;
    }
}
