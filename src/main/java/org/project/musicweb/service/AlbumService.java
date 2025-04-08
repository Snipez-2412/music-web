package org.project.musicweb.service;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.BlobInfo;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.repository.AlbumRepository;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;
    private static final String BUCKET_NAME = "music-web-project";
    private final Storage storage;

    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository, ArtistService artistService, Storage storage) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.artistService = artistService;
        this.storage = storage;
    }

    public List<AlbumEntity> getAllAlbums() {
        List<AlbumEntity> albums = albumRepository.findAll();
        albums.forEach(this::attachSignedUrls);
        return albums;
    }

    public AlbumEntity getAlbumById(Long id) {
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
        attachSignedUrls(album);
        return album;
    }

    public List<AlbumEntity> searchAlbums(AlbumCriteria criteria) {
        SpecificationUtils<AlbumEntity> builder = new SpecificationUtils<>();
        Specification<AlbumEntity> spec = builder.buildSpecification(criteria);
        return albumRepository.findAll(spec);
    }


    public AlbumEntity addAlbum(AlbumEntity album, Long artistId, String coverImagePath) {
        ArtistEntity artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        album.setArtist(artist);
        album.setCoverImage(coverImagePath);
        return albumRepository.save(album);
    }

    public AlbumEntity updateAlbum(AlbumEntity album, Long artistId, String coverImagePath) {
        AlbumEntity existingAlbum = getAlbumById(album.getAlbumID());
        ArtistEntity artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        existingAlbum.setTitle(album.getTitle());
        existingAlbum.setArtist(artist);
        if (coverImagePath != null && !coverImagePath.isEmpty()) {
            existingAlbum.setCoverImage(coverImagePath);
        }
        return albumRepository.save(existingAlbum);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    private void attachSignedUrls(AlbumEntity album) {
        if (album.getCoverImage() != null && !album.getCoverImage().isEmpty()) {
            album.setSignedCoverUrl(generateSignedUrl(album.getCoverImage()));
        }

        ArtistEntity artist = album.getArtist();
        if (artist != null) {
            artistService.attachSignedUrl(artist);
        }
    }

    private String generateSignedUrl(String fileName) {
        try {
            URL signedUrl = storage.signUrl(
                    BlobInfo.newBuilder(BUCKET_NAME, fileName).build(),
                    10, TimeUnit.MINUTES
            );
            return signedUrl.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
