package org.project.musicweb.controller;

import org.project.musicweb.dto.HistoryDTO;
import org.project.musicweb.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/recent-songs/{userID}")
    public ResponseEntity<List<HistoryDTO>> getRecentPlayedSongs(@PathVariable Long userID,
                                                                 @RequestParam(defaultValue = "10") int limit) {
        List<HistoryDTO> recentSongs = historyService.getRecentPlayedSongs(userID, limit);
        return ResponseEntity.ok(recentSongs);
    }

    @GetMapping("/recent-artists/{userID}")
    public ResponseEntity<List<HistoryDTO>> getRecentPlayedArtists(@PathVariable Long userID,
                                                                   @RequestParam(defaultValue = "10") int limit) {
        List<HistoryDTO> recentArtists = historyService.getRecentPlayedArtists(userID, limit);
        return ResponseEntity.ok(recentArtists);
    }

    @GetMapping("/recent-albums/{userID}")
    public ResponseEntity<List<HistoryDTO>> getRecentPlayedAlbums(@PathVariable Long userID,
                                                                  @RequestParam(defaultValue = "10") int limit) {
        List<HistoryDTO> recentAlbums = historyService.getRecentPlayedAlbums(userID, limit);
        return ResponseEntity.ok(recentAlbums);
    }
}
