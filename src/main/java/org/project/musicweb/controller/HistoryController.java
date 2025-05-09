package org.project.musicweb.controller;

import org.project.musicweb.dto.HistoryDTO;
import org.project.musicweb.service.HistoryService;
import org.project.musicweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;
    private final UserService userService;

    @Autowired
    public HistoryController(HistoryService historyService, UserService userService) {
        this.historyService = historyService;
        this.userService = userService;
    }

    // Add song to history
    @PostMapping("/add/songId/{songId}")
    public void addToHistory(@PathVariable Long songId, Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.getUserIdFromUsername(username);
        historyService.addToHistory(userId, songId);
    }

    // Get recently played songs
    @GetMapping("/songs")
    public List<HistoryDTO> getRecentSongs(Authentication authentication,
                                           @RequestParam(defaultValue = "10") int limit) {
        String username = authentication.getName();
        Long userId = userService.getUserIdFromUsername(username);
        return historyService.getRecentSongs(userId, limit);
    }

    // Get recently played albums
    @GetMapping("/albums")
    public List<HistoryDTO> getRecentAlbums(Authentication authentication,
                                            @RequestParam(defaultValue = "10") int limit) {
        String username = authentication.getName();
        Long userId = userService.getUserIdFromUsername(username);
        return historyService.getRecentAlbums(userId, limit);
    }

    // Get recently played artists
    @GetMapping("/artists")
    public List<HistoryDTO> getRecentArtists(Authentication authentication,
                                             @RequestParam(defaultValue = "10") int limit) {
        String username = authentication.getName();
        Long userId = userService.getUserIdFromUsername(username);
        return historyService.getRecentArtists(userId, limit);
    }
}
