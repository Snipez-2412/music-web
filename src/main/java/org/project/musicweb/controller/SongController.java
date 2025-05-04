package org.project.musicweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.LongFilter;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.dto.AlbumDTO;
import org.project.musicweb.dto.SongDTO;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.module.query.SongCriteria;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.service.SongService;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final SongRepository songRepository;

    public SongController(SongService songService, SongRepository songRepository) {
        this.songService = songService;
        this.songRepository = songRepository;
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> viewSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/search")
    public ResponseEntity<List<SongDTO>> searchSongs(SongCriteria criteria) {
        return ResponseEntity.ok(songService.searchSongs(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> viewSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<SongDTO> addSong(
            @RequestPart("song") SongDTO songDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        SongDTO saved = songService.addSong(songDTO, imageFile);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Long id, @RequestBody SongDTO songDTO) {
        SongDTO updated = songService.updateSong(id, songDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
