package org.project.musicweb.service;

import org.project.musicweb.dto.LyricsDTO;
import org.project.musicweb.dto.SongDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.project.musicweb.recommendation.api.RecommendationResult;
import org.project.musicweb.recommendation.api.LyricsRecommendationRequest;
import org.project.musicweb.repository.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendService {

    private final SongRepository songRepository;
    private final SongService songService;
    private final LyricsService lyricsService;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Value("${model.api.base-url}")
    private String baseUrl;

    private String getLyricsUrl() {
        return baseUrl.endsWith("/") ? baseUrl + "lyrics" : baseUrl + "/lyrics";
    }

    public RecommendService(SongRepository songRepository, SongService songService, LyricsService lyricsService, RestTemplate restTemplate) {
        this.songRepository = songRepository;
        this.songService = songService;
        this.lyricsService = lyricsService;
        this.restTemplate = restTemplate;
    }

    public List<SongDTO> recommendSongsBySongId(Long songId, int topK) {
        Optional<LyricsDTO> lyricsOpt = lyricsService.getLyricsBySongId(songId);
        if (lyricsOpt.isEmpty() || lyricsOpt.get().getContent() == null || lyricsOpt.get().getContent().trim().isEmpty()) {
            return Collections.emptyList();
        }

        String lyrics = lyricsOpt.get().getContent();

        if (lyrics.isBlank()  || lyrics.trim().isEmpty()) {
            return Collections.emptyList();
        }

        LyricsRecommendationRequest request = new LyricsRecommendationRequest();
        request.setLyrics(lyrics);
        request.setTop_k(topK);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LyricsRecommendationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RecommendationResult[]> response =
                restTemplate.postForEntity(getLyricsUrl(), entity, RecommendationResult[].class);

        RecommendationResult[] results = response.getBody();
        if (results == null) {
            logger.warn("Model API returned null result for song ID: {}", songId);
            return Collections.emptyList();
        }

        logger.info("Model API returned {} recommendations", results.length);
        for (RecommendationResult res : results) {
            logger.debug("Recommended song: {}, Genre: {}, Score: {}", res.getSong(), res.getGenre(), res.getSimilarity_score());
        }

        List<SongDTO> dtos = new ArrayList<>();
        for (RecommendationResult result : results) {
            songRepository.findByTitleIgnoreCase(result.getSong())
                    .ifPresent(songEntity -> {
                        SongDTO dto = songService.toDto(songEntity);
                        dtos.add(dto);
                    });
        }
        return dtos;
    }
}

