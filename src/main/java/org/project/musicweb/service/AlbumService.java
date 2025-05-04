package org.project.musicweb.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.AlbumDTO;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.repository.AlbumRepository;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final StorageService storageService;

    public AlbumService(AlbumRepository albumRepository,
                        ArtistRepository artistRepository, Storage storage, StorageService storageService) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.storageService = storageService;
    }

    public List<AlbumDTO> getAllAlbums() {
        return albumRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AlbumDTO getAlbumById(Long id) {
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
        return toDTO(album);
    }

    public List<AlbumDTO> searchAlbums(AlbumCriteria criteria) {
        SpecificationUtils<AlbumEntity> builder = new SpecificationUtils<>();
        Specification<AlbumEntity> spec = builder.buildSpecification(criteria);
        return albumRepository.findAll(spec).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AlbumDTO addAlbum(AlbumDTO albumDTO, MultipartFile imageFile) throws IOException {
        String imageFileName = storageService.uploadFile(imageFile);

        ArtistEntity artist = artistRepository.findById(albumDTO.getArtistID())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        AlbumEntity album = new AlbumEntity();
        album.setTitle(albumDTO.getTitle());
        album.setGenre(albumDTO.getGenre());
        album.setReleaseDate(albumDTO.getReleaseDate());
        album.setCoverImage(imageFileName);
        album.setArtist(artist);

        AlbumEntity saved = albumRepository.save(album);
        return toDTO(saved);
    }

    public AlbumDTO updateAlbum(AlbumDTO albumDTO) {
        AlbumEntity existingAlbum = albumRepository.findById(albumDTO.getAlbumID())
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));

        ArtistEntity artist = artistRepository.findById(albumDTO.getArtistID())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        existingAlbum.setTitle(albumDTO.getTitle());
        existingAlbum.setReleaseDate(albumDTO.getReleaseDate());
        existingAlbum.setGenre(albumDTO.getGenre());
        existingAlbum.setCoverImage(albumDTO.getCoverImage());
        existingAlbum.setArtist(artist);

        AlbumEntity updated = albumRepository.save(existingAlbum);
        return toDTO(updated);
    }


    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    private AlbumDTO toDTO(AlbumEntity album) {
        String signedUrl = StringUtils.isNotBlank(album.getCoverImage())
                ? storageService.generateSignedUrl(album.getCoverImage()) : null;

        return AlbumDTO.entityToDTO(album, signedUrl);
    }

}
