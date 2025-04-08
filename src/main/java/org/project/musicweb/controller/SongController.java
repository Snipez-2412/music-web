package org.project.musicweb.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.common.filter.LongFilter;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.SongCriteria;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.service.SongService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<SongEntity>> viewSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/search")
    public ResponseEntity<List<SongEntity>> searchSongs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Long albumId
    ) {
        SongCriteria criteria = new SongCriteria();

        // Title filter
        if (StringUtils.isNotBlank(title)) {
            StringFilter titleFilter = new StringFilter();
            titleFilter.setContains(title);
            criteria.setTitle(titleFilter);
        }

        // Genre filter
        if (StringUtils.isNotBlank(genre)) {
            StringFilter genreFilter = new StringFilter();
            genreFilter.setContains(genre);
            criteria.setTitle(genreFilter);
        }

        // Album ID filter
        if (albumId != null) {
            LongFilter albumIdFilter = new LongFilter();
            albumIdFilter.setEquals(albumId);
            criteria.setAlbumId(albumIdFilter);
        }

        List<SongEntity> songs = songService.searchSongs(criteria);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongEntity> viewSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PostMapping
    public ResponseEntity<SongEntity> addSong(@RequestBody SongEntity song) {
        return ResponseEntity.ok(songService.addSong(song, song.getAlbum().getAlbumID()));
    }

    @PutMapping
    public ResponseEntity<SongEntity> updateSong(@PathVariable long id, @RequestBody SongEntity song) {
        SongEntity updatedSong = songService.updateSong(id, song);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
