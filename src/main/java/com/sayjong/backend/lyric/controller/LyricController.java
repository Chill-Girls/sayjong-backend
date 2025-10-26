package com.sayjong.backend.lyric.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.sayjong.backend.lyric.dto.request.AppLyricRequest;
import com.sayjong.backend.lyric.service.LyricOrchestrationService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lyrics")
public class LyricController {
	private final LyricOrchestrationService lyricOrchestrationService;

	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> createLyrics(@Valid @RequestBody AppLyricRequest request) {

		log.info("[LyricController] Received lyric creation request for songId: {}", request.songId());

		try {
			boolean isCreated = lyricOrchestrationService.createAndSaveLyrics(request);

			if (isCreated) {
				// 201 Created: 성공적으로 생성됨
				Map<String, String> response = Map.of("message", "Lyrics created successfully for songId: " + request.songId());
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				// 200 OK: 이미 존재하여 건너뜀
				Map<String, String> response = Map.of("message", "Lyrics already exist for songId: " + request.songId() + ". Skipped.");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

		} catch (EntityNotFoundException e) {
			log.warn("[LyricController] Not found: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("error", e.getMessage()));

		} catch (WebClientResponseException e) {
			log.error("[LyricController] Error from worker: {} - {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
			return ResponseEntity.status(e.getStatusCode())
				.body(Map.of("error", "Error from worker service: " + e.getResponseBodyAsString()));

		} catch (Exception e) {
			log.error("[LyricController] Internal server error: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
		}
	}
}
