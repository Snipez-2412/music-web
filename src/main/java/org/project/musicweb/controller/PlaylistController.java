package org.project.musicweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.module.query.PlaylistCriteria;
import org.project.musicweb.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistEntity>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaylistEntity>> searchPlaylists(
            @RequestParam String name
    ) {
        PlaylistCriteria criteria = new PlaylistCriteria();

        // Name filter
        if (StringUtils.isNotBlank(name)) {
            StringFilter nameFilter = new StringFilter();
            nameFilter.setContains(name);
            criteria.setName(nameFilter);
        }

        List<PlaylistEntity> playlists = playlistService.searchPlaylists(criteria);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistEntity> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    @PostMapping
    public ResponseEntity<PlaylistEntity> addPlaylist(@RequestBody PlaylistEntity playlist) {
        return ResponseEntity.ok(playlistService.addPlaylist(playlist));
    }

    @PutMapping
    public ResponseEntity<PlaylistEntity> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistEntity playlist) {
        return ResponseEntity.ok(playlistService.updatePlaylist(id, playlist));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
