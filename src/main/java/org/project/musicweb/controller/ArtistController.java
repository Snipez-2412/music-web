package org.project.musicweb.controller;

import org.project.musicweb.dto.ArtistDTO;
import org.project.musicweb.module.query.ArtistCriteria;
import org.project.musicweb.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> viewArtists() {
        List<ArtistDTO> artistDTOs = artistService.getAllArtists();
        return ResponseEntity.ok(artistDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistDTO>> searchArtists(ArtistCriteria criteria) {
        List<ArtistDTO> artistDTOs = artistService.searchArtists(criteria);
        return ResponseEntity.ok(artistDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long id) {
        ArtistDTO artistDTO = artistService.getArtistById(id);
        return ResponseEntity.ok(artistDTO);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ArtistDTO> addArtist(
            @RequestPart("artist") ArtistDTO artistDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        return ResponseEntity.ok(artistService.addArtist(artistDTO, imageFile));
    }

    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ArtistDTO> updateArtist(
            @RequestPart("artist") ArtistDTO artistDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        ArtistDTO updated = artistService.updateArtist(artistDTO, imageFile);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
