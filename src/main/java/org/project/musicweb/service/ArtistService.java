package org.project.musicweb.service;


import com.google.cloud.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.ArtistDTO;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.module.query.ArtistCriteria;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final StorageService storageService;

    public ArtistService(ArtistRepository artistRepository, Storage storage, StorageService storageService) {
        this.artistRepository = artistRepository;
        this.storageService = storageService;
    }

    public List<ArtistDTO> getAllArtists() {
        List<ArtistEntity> artists = artistRepository.findAll();
        return artists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ArtistDTO> searchArtists(ArtistCriteria criteria) {
        SpecificationUtils<ArtistEntity> builder = new SpecificationUtils<>();
        Specification<ArtistEntity> spec = builder.buildSpecification(criteria);
        List<ArtistEntity> artists = artistRepository.findAll(spec);
        return artists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ArtistDTO getArtistById(Long id) {
        ArtistEntity artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        return toDto(artist);
    }

    public ArtistDTO addArtist(ArtistDTO artistDTO, MultipartFile imageFile) throws IOException {
        String imageFileName = storageService.uploadFile(imageFile);

        ArtistEntity artist = new ArtistEntity();
        artist.setName(artistDTO.getName());
        artist.setBio(artistDTO.getBio());
        artist.setProfilePic(imageFileName);

        ArtistEntity savedArtist = artistRepository.save(artist);
        return toDto(savedArtist);
    }

    public ArtistDTO updateArtist(ArtistDTO artistDTO) {
        ArtistEntity existingArtist = artistRepository.findById(artistDTO.getArtistID())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        existingArtist.setName(artistDTO.getName());
        existingArtist.setBio(artistDTO.getBio());

        if (StringUtils.isNotBlank(artistDTO.getProfilePic())) {
            existingArtist.setProfilePic(artistDTO.getProfilePic());
        }

        ArtistEntity updated = artistRepository.save(existingArtist);
        return toDto(updated);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    private ArtistDTO toDto(ArtistEntity artist) {
        String signedUrl = StringUtils.isNotBlank(artist.getProfilePic())
                ? storageService.generateSignedUrl(artist.getProfilePic())
                : null;

        return ArtistDTO.entityToDto(artist, signedUrl);
    }
}
