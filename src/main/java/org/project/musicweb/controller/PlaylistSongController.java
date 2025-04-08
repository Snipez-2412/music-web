package org.project.musicweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.entity.PlaylistSongEntity;
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
    public ResponseEntity<List<PlaylistSongEntity>> searchPlaylistSongs(
            @RequestParam(required = false) String songTitle,
            @RequestParam(required = false) String albumTitle,
            @RequestParam(required = false) String artistName) {

        PlaylistSongCriteria criteria = new PlaylistSongCriteria();

        if (StringUtils.isNotBlank(songTitle)) {
            StringFilter songTitleFilter = new StringFilter();
            songTitleFilter.setContains(songTitle);
            criteria.setSongTitle(songTitleFilter);
        }

        if (StringUtils.isNotBlank(albumTitle)) {
            StringFilter albumTitleFilter = new StringFilter();
            albumTitleFilter.setContains(albumTitle);
            criteria.setAlbumTitle(albumTitleFilter);
        }

        if (StringUtils.isNotBlank(artistName)) {
            StringFilter artistNameFilter = new StringFilter();
            artistNameFilter.setContains(artistName);
            criteria.setArtistName(artistNameFilter);
        }

        List<PlaylistSongEntity> result = playlistSongService.searchPlaylistSongs(criteria);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{playlistId}")
    public ResponseEntity<List<PlaylistSongEntity>> getSongsInPlaylist(@PathVariable Long playlistId) {
        return ResponseEntity.ok(playlistSongService.getSongsByPlaylistId(playlistId));
    }

    @PostMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<PlaylistSongEntity> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return ResponseEntity.ok(playlistSongService.addSongToPlaylist(playlistId, songId));
    }

    @DeleteMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistSongService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.noContent().build();
    }
}
