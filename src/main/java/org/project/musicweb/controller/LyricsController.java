package org.project.musicweb.controller;

import org.project.musicweb.dto.LyricsDTO;
import org.project.musicweb.service.LyricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/lyrics")
public class LyricsController {
    private final LyricsService lyricsService;

    public LyricsController(LyricsService lyricsService) {
        this.lyricsService = lyricsService;
    }

    @GetMapping("/{songId}")
    public ResponseEntity<LyricsDTO> getLyrics(@PathVariable Long songId) {
        Optional<LyricsDTO> lyrics = lyricsService.getLyricsBySongId(songId);
        return lyrics.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{songId}")
    public ResponseEntity<LyricsDTO> addLyrics(@PathVariable Long songId, @RequestBody String content) {
        LyricsDTO lyrics = lyricsService.addLyrics(songId, content);
        return ResponseEntity.ok(lyrics);
    }

    @PutMapping("/update/{songId}")
    public ResponseEntity<LyricsDTO> updateLyrics(@PathVariable Long songId, @RequestBody String newContent) {
        LyricsDTO updatedLyrics = lyricsService.updateLyrics(songId, newContent);
        return ResponseEntity.ok(updatedLyrics);
    }

    @DeleteMapping("/delete/{songId}")
    public ResponseEntity<Void> deleteLyrics(@PathVariable Long songId) {
        lyricsService.deleteLyrics(songId);
        return ResponseEntity.noContent().build();
    }
}
