package org.project.musicweb.controller;

import org.project.musicweb.dto.SongDTO;
import org.project.musicweb.recommendation.api.RecommendationResult;
import org.project.musicweb.service.RecommendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping("/lyrics/songId/{songId}")
    public ResponseEntity<List<SongDTO>> recommendBySongId(
            @PathVariable Long songId,
            @RequestParam(defaultValue = "5") int topK) {

        List<SongDTO> recommendedSongs = recommendService.recommendSongsBySongId(songId, topK);
        return ResponseEntity.ok(recommendedSongs);
    }
}
