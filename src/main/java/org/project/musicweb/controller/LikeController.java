package org.project.musicweb.controller;

import org.project.musicweb.dto.LikeDTO;
import org.project.musicweb.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDTO>> getLikedSongs(@PathVariable Long userId) {
        List<LikeDTO> likedSongs = likeService.getLikedSongsByUser(userId);
        return ResponseEntity.ok(likedSongs);
    }

    @PostMapping("/like/user/{userId}/song/{songId}")
    public ResponseEntity<String> likeSong(@PathVariable Long userId, @PathVariable Long songId) {
        try {
            likeService.likeSong(userId, songId);
            return ResponseEntity.ok("Song liked successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/unlike/user/{userId}/songs/{songId}")
    public ResponseEntity<String> unlikeSong(@PathVariable Long userId, @PathVariable Long songId) {
        try {
            likeService.unlikeSong(userId, songId);
            return ResponseEntity.ok("Song unliked successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
