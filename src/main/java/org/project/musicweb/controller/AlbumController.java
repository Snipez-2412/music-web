package org.project.musicweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.DateFilter;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumEntity>> viewAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlbumEntity>> searchAlbums(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String releaseDateBefore,
            @RequestParam(required = false) String releaseDateAfter,
            @RequestParam(required = false) String releaseDateEquals
    ) {
        AlbumCriteria criteria = new AlbumCriteria();

        // Title filter
        if (StringUtils.isNotBlank(title)) {
            StringFilter titleFilter = new StringFilter();
            titleFilter.setContains(title);
            criteria.setTitle(titleFilter);
        }

        // Date filter
        DateFilter dateFilter = new DateFilter();
        boolean hasDateFilter = false;

        if (StringUtils.isNotBlank(releaseDateEquals)) {
            dateFilter.setEquals(LocalDate.parse(releaseDateEquals));
            hasDateFilter = true;
        }

        if (StringUtils.isNotBlank(releaseDateBefore)) {
            dateFilter.setBefore(LocalDate.parse(releaseDateBefore));
            hasDateFilter = true;
        }

        if (StringUtils.isNotBlank(releaseDateAfter)) {
            dateFilter.setAfter(LocalDate.parse(releaseDateAfter));
            hasDateFilter = true;
        }

        if (hasDateFilter) {
            criteria.setReleaseDate(dateFilter);
        }

        List<AlbumEntity> albums = albumService.searchAlbums(criteria);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumEntity> getAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @PostMapping()
    public ResponseEntity<AlbumEntity> addAlbum(@RequestBody AlbumEntity album) {
        return ResponseEntity.ok(albumService.addAlbum(album, album.getArtist().getArtistID(), album.getCoverImage()));
    }

    @PutMapping()
    public ResponseEntity<AlbumEntity> updateAlbum(@RequestBody AlbumEntity album) {
        return ResponseEntity.ok(albumService.updateAlbum(album, album.getArtist().getArtistID(), album.getCoverImage()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
