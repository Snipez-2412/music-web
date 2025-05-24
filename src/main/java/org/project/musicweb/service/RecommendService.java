package org.project.musicweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.musicweb.dto.AlbumDTO;
import org.project.musicweb.dto.LyricsDTO;
import org.project.musicweb.dto.SongDTO;
import org.project.musicweb.recommendation.api.RecommendationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.project.musicweb.recommendation.api.RecommendationResult;
import org.project.musicweb.recommendation.api.LyricsRecommendationRequest;
import org.project.musicweb.repository.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.SysexMessage;
import java.util.*;

@Service
public class RecommendService {

    private final SongRepository songRepository;
    private final SongService songService;
    private final AlbumService albumService;
    private final LyricsService lyricsService;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Value("${model.api.base-url}")
    private String baseUrl;

    private String getLyricsUrl() {
        return baseUrl.endsWith("/") ? baseUrl + "lyrics" : baseUrl + "/lyrics";
    }

    private String getSongNameUrl() {
        return baseUrl.endsWith("/") ? baseUrl + "song-name" : baseUrl + "/song-name";
    }

    public RecommendService(SongRepository songRepository, SongService songService, AlbumService albumService, LyricsService lyricsService, RestTemplate restTemplate) {
        this.songRepository = songRepository;
        this.songService = songService;
        this.albumService = albumService;
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
            logger.debug("Recommended song: {}, Genre: {}, Score: {}", res.getSong(), res.getGenre(), res.getScore());
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

    public List<AlbumDTO> recommendAlbumsBySongId(Long songId, int topK) {
        Optional<SongDTO> songOpt = Optional.ofNullable(songService.getSongById(songId));
        if (songOpt.isEmpty()) {
            logger.warn("Song with ID {} not found.", songId);
            return Collections.emptyList();
        }

        SongDTO song = songOpt.get();
        String songName = song.getTitle();
        String songGenre = song.getGenre();

        logger.info("Found song: {} with genre: {}", songName, songGenre);

        RecommendationRequest request = new RecommendationRequest();
        request.setDescription(songName);
        request.setTop_k(topK);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecommendationRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(getSongNameUrl(), entity, String.class);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.warn("Model API returned 404: {}", response.getBody());
                return Collections.emptyList(); // Handle "not found" case
            }

            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                logger.error("Model API returned 500: {}", response.getBody());
                return Collections.emptyList(); // Handle unexpected errors
            }

            ObjectMapper objectMapper = new ObjectMapper();
            RecommendationResult[] results = objectMapper.readValue(response.getBody(), RecommendationResult[].class);

            if (results == null || results.length == 0) {
                logger.warn("Model API returned an empty or null response for song name: {}", songName);
                return Collections.emptyList();
            }

            logger.info("Model API returned {} recommendations", results.length);
            for (RecommendationResult res : results) {
                logger.debug("Recommended song: {}, Genre: {}, Similarity: {}", res.getSong(), res.getGenre(), res.getScore());
            }

            Set<Long> albumIds = new HashSet<>();
            List<AlbumDTO> albums = new ArrayList<>();

            for (RecommendationResult result : results) {
                songRepository.findByTitleIgnoreCase(result.getSong())
                        .ifPresent(songEntity -> {
                            Long albumId = songEntity.getAlbum().getAlbumID();
                            if (albumId != null && !albumIds.contains(albumId)) {
                                AlbumDTO album = albumService.getAlbumById(albumId);
                                albums.add(album);
                                albumIds.add(albumId);
                            }
                        });
            }

            return albums;
        } catch (RestClientException e) {
            logger.error("Error while calling model API: {}", e.getMessage(), e);
            return Collections.emptyList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

