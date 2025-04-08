package org.project.musicweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.module.query.ArtistCriteria;
import org.project.musicweb.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistEntity>> viewArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistEntity>> searchArtists(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country
    ) {
        ArtistCriteria criteria = new ArtistCriteria();

        // Name filter
        if (StringUtils.isNotBlank(name)) {
            StringFilter nameFilter = new StringFilter();
            nameFilter.setContains(name);
            criteria.setName(nameFilter);
        }

        // Country filter
        if (StringUtils.isNotBlank(country)) {
            StringFilter countryFilter = new StringFilter();
            countryFilter.setContains(country);
            criteria.setCountry(countryFilter);
        }

        List<ArtistEntity> artists = artistService.searchArtists(criteria);

        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistEntity> getArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PostMapping
    public ResponseEntity<ArtistEntity> addArtist(@RequestBody ArtistEntity artist) {
        return ResponseEntity.ok(artistService.addArtist(artist, artist.getProfilePic()));
    }

    @PutMapping
    public ResponseEntity<ArtistEntity> updateArtist(@RequestBody ArtistEntity artist) {
        return ResponseEntity.ok(artistService.updateArtist(artist, artist.getProfilePic()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
