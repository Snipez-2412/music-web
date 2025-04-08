package org.project.musicweb.service;

import com.google.cloud.storage.Storage;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.SongCriteria;
import org.project.musicweb.repository.AlbumRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private static final String BUCKET_NAME = "music-web-project";
    private final Storage storage;

    public SongService(SongRepository songRepository, AlbumRepository albumRepository, Storage storage) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.storage = storage;
    }

    public List<SongEntity> getAllSongs() {
        List<SongEntity> songs = songRepository.findAll();
        songs.forEach(this::attachSignedUrl);
        return songs;
    }

    public List<SongEntity> searchSongs(SongCriteria criteria) {
        SpecificationUtils<SongEntity> builder = new SpecificationUtils<>();
        Specification<SongEntity> spec = builder.buildSpecification(criteria);
        return songRepository.findAll(spec);
    }

    public SongEntity getSongById(Long id) {
        SongEntity song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        attachSignedUrl(song);
        return song;
    }

    public SongEntity addSong(SongEntity song, Long albumId) {
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        song.setAlbum(album);
        song.setArtist(album.getArtist());
        return songRepository.save(song);
    }

    public SongEntity updateSong(Long id, SongEntity song) {
        SongEntity existingSong = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (song.getTitle() != null) {
            existingSong.setTitle(song.getTitle());
        }
        if (song.getDuration() != null) {
            existingSong.setDuration(song.getDuration());
        }
        if (song.getGenre() != null) {
            existingSong.setGenre(song.getGenre());
        }
        if (song.getFilePath() != null && !song.getFilePath().isEmpty()) {
            existingSong.setFilePath(song.getFilePath());
        }
        return songRepository.save(existingSong);
    }


    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    private void attachSignedUrl(SongEntity song) {
        if (song.getFilePath() != null && !song.getFilePath().isEmpty()) {
            URL signedUrl = generateSignedUrl(song.getFilePath());
            song.setSignedFilePath(signedUrl.toString());
        }
    }

    private URL generateSignedUrl(String filePath) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, filePath).build();
        return storage.signUrl(blobInfo, 10, TimeUnit.MINUTES);
    }


}
