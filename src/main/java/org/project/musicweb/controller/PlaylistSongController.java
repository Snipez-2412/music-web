package org.project.musicweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.dto.PlaylistSongDTO;
import org.project.musicweb.module.query.PlaylistSongCriteria;
import org.project.musicweb.service.PlaylistSongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist-songs")
public class PlaylistSongController {
    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(PlaylistSongService playlistSongService) {
        this.playlistSongService = playlistSongService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaylistSongDTO>> searchPlaylistSongs(PlaylistSongCriteria criteria) {
        return ResponseEntity.ok(playlistSongService.searchPlaylistSongs(criteria));
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<List<PlaylistSongDTO>> getSongsInPlaylist(@PathVariable Long playlistId) {
        List<PlaylistSongDTO> playlistSongs = playlistSongService.getSongsByPlaylistId(playlistId);
        return ResponseEntity.ok(playlistSongs);
    }

    @PostMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<PlaylistSongDTO> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        PlaylistSongDTO playlistSong = playlistSongService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok(playlistSong);
    }

    @DeleteMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistSongService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.noContent().build();
    }
}
