package org.project.musicweb.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.entity.UserEntity;
import org.project.musicweb.module.query.PlaylistCriteria;
import org.project.musicweb.repository.PlaylistRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final Storage storage;
    private static final String BUCKET_NAME = "music-web-project";

    public PlaylistService(PlaylistRepository playlistRepository, Storage storage) {
        this.playlistRepository = playlistRepository;
        this.storage = storage;
    }

    public List<PlaylistEntity> getAllPlaylists() {
        List<PlaylistEntity> playlists = playlistRepository.findAll();
        playlists.forEach(this::attachSignedUrls);
        return playlists;
    }

    public PlaylistEntity getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    attachSignedUrls(playlist);
                    return playlist;
                })
                .orElse(null);
    }

    public List<PlaylistEntity> searchPlaylists(PlaylistCriteria criteria) {
        SpecificationUtils<PlaylistEntity> builder = new SpecificationUtils<>();
        Specification<PlaylistEntity> spec = builder.buildSpecification(criteria);
        return playlistRepository.findAll(spec);
    }

    public PlaylistEntity addPlaylist(PlaylistEntity playlist) {
        return playlistRepository.save(playlist);
    }

    public PlaylistEntity updatePlaylist(Long id, PlaylistEntity updatedPlaylist) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlist.setName(updatedPlaylist.getName());
                    playlist.setCoverImage(updatedPlaylist.getCoverImage());
                    attachSignedUrls(playlist);
                    return playlistRepository.save(playlist);
                })
                .orElse(null);
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    private void attachSignedUrls(PlaylistEntity playlist) {
        if (playlist.getCoverImage() != null && !playlist.getCoverImage().isEmpty()) {
            playlist.setSignedCoverUrl(generateSignedUrl(playlist.getCoverImage()));
        }

        UserEntity owner = playlist.getUser();
        if (owner != null) {
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
