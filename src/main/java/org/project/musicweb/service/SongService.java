package org.project.musicweb.service;

import com.google.cloud.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.SongDTO;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.SongCriteria;
import org.project.musicweb.repository.AlbumRepository;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final StorageService storageService;

    public SongService(SongRepository songRepository,
                       AlbumRepository albumRepository, Storage storage, ArtistRepository artistRepository, StorageService storageService) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.storageService = storageService;
    }

    public List<SongDTO> getAllSongs() {
        List<SongEntity> songs = songRepository.findAll();
        return songs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<SongDTO> searchSongs(SongCriteria criteria) {
        SpecificationUtils<SongEntity> builder = new SpecificationUtils<>();
        Specification<SongEntity> spec = builder.buildSpecification(criteria);
        List<SongEntity> songs = songRepository.findAll(spec);
        return songs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SongDTO getSongById(Long id) {
        SongEntity song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        return toDto(song);
    }

    public SongDTO addSong(SongDTO songDTO, MultipartFile imageFile, MultipartFile audioFile) throws IOException {
        // Upload image if provided
        String imageFileName = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFileName = storageService.uploadFile(imageFile);
        }

        // Upload audio file (MP3)
        String audioFileName = null;
        if (audioFile != null && !audioFile.isEmpty()) {
            audioFileName = storageService.uploadFile(audioFile);
        }

        SongEntity song = new SongEntity();
        song.setTitle(songDTO.getTitle());
        song.setGenre(songDTO.getGenre());
        song.setDuration(songDTO.getDuration());
        song.setCoverImage(imageFileName);
        song.setFilePath(audioFileName);

        // Assign album and artist
        if (songDTO.getAlbumID() != null) {
            AlbumEntity album = albumRepository.findById(songDTO.getAlbumID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));
            song.setAlbum(album);
            song.setArtist(album.getArtist()); // Assume album implies artist
        } else {
            throw new IllegalArgumentException("Album must be specified");
        }

        SongEntity saved = songRepository.save(song);
        return toDto(saved);
    }


    public SongDTO updateSong(SongDTO songDTO, MultipartFile imageFile, MultipartFile audioFile) throws IOException {
        SongEntity song = songRepository.findById(songDTO.getSongID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
        // Update artist
        ArtistEntity artist = artistRepository.findById(songDTO.getArtistID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        // Update album if provided
        if (songDTO.getAlbumID() != null) {
            AlbumEntity album = albumRepository.findById(songDTO.getAlbumID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));
            song.setAlbum(album);
        } else {
            song.setAlbum(null);
        }

        // Handle image file upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = storageService.uploadFile(imageFile);
            song.setCoverImage(imageFileName);
        }

        String imageFileName = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFileName = storageService.uploadFile(imageFile);
        }

        // Upload audio file (MP3)
        String audioFileName = null;
        if (audioFile != null && !audioFile.isEmpty()) {
            audioFileName = storageService.uploadFile(audioFile);
        }

        song.setTitle(songDTO.getTitle());
        song.setArtist(artist);
        song.setCoverImage(imageFileName);
        song.setFilePath(audioFileName);

        // Save the updated song
        SongEntity saved = songRepository.save(song);
        return toDto(saved);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    private SongDTO toDto(SongEntity song) {
        String signedCoverUrl = StringUtils.isNotBlank(song.getCoverImage())
                ? storageService.generateSignedUrl(song.getCoverImage()) : null;
        String signedFilePath = StringUtils.isNotBlank(song.getFilePath())
                ? storageService.generateSignedUrl(song.getFilePath()) : null;
        return SongDTO.entityToDto(song, signedFilePath, signedCoverUrl);
    }
}
