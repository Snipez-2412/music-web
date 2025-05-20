package org.project.musicweb.controller;

import org.project.musicweb.dto.PlaylistDTO;
import org.project.musicweb.module.query.PlaylistCriteria;
import org.project.musicweb.service.PlaylistService;
import org.project.musicweb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final UserService userService;

    public PlaylistController(PlaylistService playlistService, UserService userService) {
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long userId = userService.getUserIdFromUsername(username);
        List<PlaylistDTO> playlists = playlistService.getPlaylistsByUserId(userId);

        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaylistDTO>> searchPlaylists(PlaylistCriteria criteria) {
        return ResponseEntity.ok(playlistService.searchPlaylists(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistById(@PathVariable Long id) {
        PlaylistDTO playlist = playlistService.getPlaylistById(id);
        return playlist != null ? ResponseEntity.ok(playlist) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PlaylistDTO> addPlaylist(
            @RequestPart("playlist") PlaylistDTO playlistDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserIdFromUsername(username);
        playlistDTO.setCreatedByUserID(userId);
        PlaylistDTO save = playlistService.addPlaylist(playlistDTO, imageFile);
        return ResponseEntity.ok(save);
    }

    @PutMapping(path = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<PlaylistDTO> updatePlaylist(
            @PathVariable Long id,
            @RequestPart("playlist") PlaylistDTO playlistDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        PlaylistDTO updated = playlistService.updatePlaylist(id, playlistDTO, imageFile);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
