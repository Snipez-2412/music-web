package org.project.musicweb.controller;

import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("//history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    // Get recent songs
    @GetMapping("/recent-songs/{userID}")
    public ResponseEntity<List<SongEntity>> getRecentPlayedSongs(@PathVariable Long userID,
                                                                 @RequestParam(defaultValue = "10") int limit) {
        List<SongEntity> recentSongs = historyService.getRecentPlayedSongs(userID, limit);
        return ResponseEntity.ok(recentSongs);
    }

    // Get recent artists
    @GetMapping("/recent-artists/{userID}")
    public ResponseEntity<List<ArtistEntity>> getRecentPlayedArtists(@PathVariable Long userID,
                                                                     @RequestParam(defaultValue = "10") int limit) {
        List<ArtistEntity> recentArtists = historyService.getRecentPlayedArtists(userID, limit);
        return ResponseEntity.ok(recentArtists);
    }

    // Get recent albums
    @GetMapping("/recent-albums/{userID}")
    public ResponseEntity<List<AlbumEntity>> getRecentPlayedAlbums(@PathVariable Long userID,
                                                                   @RequestParam(defaultValue = "10") int limit) {
        List<AlbumEntity> recentAlbums = historyService.getRecentPlayedAlbums(userID, limit);
        return ResponseEntity.ok(recentAlbums);
    }
}
