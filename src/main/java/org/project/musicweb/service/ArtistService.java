package org.project.musicweb.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.module.query.AlbumCriteria;
import org.project.musicweb.module.query.ArtistCriteria;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final Storage storage;
    private static final String BUCKET_NAME = "music-web-project";

    public ArtistService(ArtistRepository artistRepository, Storage storage) {
        this.artistRepository = artistRepository;
        this.storage = storage;
    }

    public List<ArtistEntity> getAllArtists() {
        List<ArtistEntity> artists = artistRepository.findAll();
        artists.forEach(this::attachSignedUrl);
        return artists;
    }

    public List<ArtistEntity> searchArtists(ArtistCriteria criteria) {
        SpecificationUtils<ArtistEntity> builder = new SpecificationUtils<>();
        Specification<ArtistEntity> spec = builder.buildSpecification(criteria);
        return artistRepository.findAll(spec);
    }

    public ArtistEntity getArtistById(Long id) {
        ArtistEntity artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        attachSignedUrl(artist);
        return artist;
    }

    public ArtistEntity addArtist(ArtistEntity artist, String profilePicPath) {
        if (profilePicPath != null && !profilePicPath.isEmpty()) {
            artist.setProfilePic(profilePicPath);
        }
        return artistRepository.save(artist);
    }

    public ArtistEntity updateArtist(ArtistEntity artist, String profilePicPath) {
        ArtistEntity existingArtist = getArtistById(artist.getArtistID());
        existingArtist.setName(artist.getName());
        existingArtist.setBio(artist.getBio());
        existingArtist.setCountry(artist.getCountry());
        if (profilePicPath != null && !profilePicPath.isEmpty()) {
            existingArtist.setProfilePic(profilePicPath);
        }
        return artistRepository.save(existingArtist);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    void attachSignedUrl(ArtistEntity artist) {
        if (artist.getProfilePic() != null && !artist.getProfilePic().isEmpty()) {
            try {
                URL signedUrl = storage.signUrl(
                        BlobInfo.newBuilder(BUCKET_NAME, artist.getProfilePic()).build(),
                        10, TimeUnit.MINUTES
                );
                artist.setSignedProfileUrl(signedUrl.toString());
            } catch (Exception e) {
                e.printStackTrace();
                artist.setSignedProfileUrl(null);
            }
        }
    }
}
