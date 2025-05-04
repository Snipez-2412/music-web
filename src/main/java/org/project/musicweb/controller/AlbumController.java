package org.project.musicweb.controller;

import org.project.musicweb.dto.AlbumDTO;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> viewAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlbumDTO>> searchAlbums(AlbumCriteria criteria) {
        return ResponseEntity.ok(albumService.searchAlbums(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<AlbumDTO> addAlbum(
            @RequestPart("album") AlbumDTO albumDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        AlbumDTO saved = albumService.addAlbum(albumDTO, imageFile);
        return ResponseEntity.ok(saved);
    }

    @PutMapping
    public ResponseEntity<AlbumDTO> updateAlbum(@RequestBody AlbumDTO albumDTO) {
        AlbumDTO updated = albumService.updateAlbum(albumDTO);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
