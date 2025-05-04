package org.project.musicweb.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.SongDTO;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.SongCriteria;
import org.project.musicweb.repository.AlbumRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final StorageService storageService;

    public SongService(SongRepository songRepository,
                       AlbumRepository albumRepository, Storage storage, StorageService storageService) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
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

    public SongDTO addSong(SongDTO songDTO, MultipartFile imageFile) throws IOException {
        String imageFileName = storageService.uploadFile(imageFile);

        SongEntity song = new SongEntity();
        song.setTitle(songDTO.getTitle());
        song.setGenre(songDTO.getGenre());
        song.setDuration(songDTO.getDuration());
        song.setFilePath(songDTO.getFilePath());
        song.setCoverImage(imageFileName);

        if (songDTO.getAlbumID() != null) {
            AlbumEntity album = albumRepository.findById(songDTO.getAlbumID())
                    .orElseThrow(() -> new RuntimeException("Album not found"));
            song.setAlbum(album);
            song.setArtist(album.getArtist());
        }

        SongEntity saved = songRepository.save(song);
        return toDto(saved);
    }


    public SongDTO updateSong(Long id, SongDTO songDTO) {
        SongEntity existingSong = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        existingSong.setTitle(songDTO.getTitle());
        existingSong.setDuration(songDTO.getDuration());
        existingSong.setGenre(songDTO.getGenre());
        existingSong.setFilePath(songDTO.getFilePath());
        existingSong.setCoverImage(songDTO.getCoverImage());

        SongEntity updated = songRepository.save(existingSong);
        return toDto(updated);
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
