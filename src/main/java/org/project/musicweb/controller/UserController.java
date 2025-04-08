package org.project.musicweb.controller;


import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search/songs")
    public ResponseEntity<List<SongEntity>> searchSongs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String albumTitle,
            @RequestParam(required = false) String artistName) {

        List<SongEntity> songs = userService.searchSongs(title, albumTitle, artistName);
        return ResponseEntity.ok(songs);
    }

}
