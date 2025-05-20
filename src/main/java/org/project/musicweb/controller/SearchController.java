package org.project.musicweb.controller;

import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.dto.AlbumDTO;
import org.project.musicweb.dto.ArtistDTO;
import org.project.musicweb.dto.SearchDTO;
import org.project.musicweb.dto.SongDTO;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.module.query.ArtistCriteria;
import org.project.musicweb.module.query.SongCriteria;
import org.project.musicweb.service.AlbumService;
import org.project.musicweb.service.ArtistService;
import org.project.musicweb.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SongService songService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    public SearchController(SongService songService, AlbumService albumService, ArtistService artistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<SearchDTO>> searchAll(@RequestParam("query") String query) {
        List<SearchDTO> results = new ArrayList<>();

        // Build SongCriteria search based on title
        SongCriteria songCriteria = new SongCriteria();
        StringFilter songTitleFilter = new StringFilter();
        songTitleFilter.setContains(query);
        songCriteria.setTitle(songTitleFilter);

        // Build AlbumCriteria search based on title
        AlbumCriteria albumCriteria = new AlbumCriteria();
        StringFilter albumTitleFilter = new StringFilter();
        albumTitleFilter.setContains(query);
        albumCriteria.setTitle(albumTitleFilter);

        // Build ArtistCriteria search based on name
        ArtistCriteria artistCriteria = new ArtistCriteria();
        StringFilter artistNameFilter = new StringFilter();
        artistNameFilter.setContains(query);
        artistCriteria.setName(artistNameFilter);

        List<SongDTO> songs = songService.searchSongs(songCriteria);
        for (SongDTO song : songs) {
            results.add(new SearchDTO("song", song.getSongID(), song.getTitle(), song.getSignedCoverUrl()));
        }

        List<AlbumDTO> albums = albumService.searchAlbums(albumCriteria);
        for (AlbumDTO album : albums) {
            results.add(new SearchDTO("album", album.getAlbumID(), album.getTitle(), album.getSignedCoverUrl()));
        }

        List<ArtistDTO> artists = artistService.searchArtists(artistCriteria);
        for (ArtistDTO artist : artists) {
            results.add(new SearchDTO("artist", artist.getArtistID(), artist.getName(), artist.getSignedProfileUrl()));
        }

        return ResponseEntity.ok(results);
    }
}
