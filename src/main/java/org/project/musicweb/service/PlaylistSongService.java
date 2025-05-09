package org.project.musicweb.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.project.musicweb.common.filter.LongFilter;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.dto.PlaylistSongDTO;
import org.project.musicweb.entity.*;
import org.project.musicweb.module.query.PlaylistSongCriteria;
import org.project.musicweb.repository.PlaylistRepository;
import org.project.musicweb.repository.PlaylistSongRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final StorageService storageService;

    public PlaylistSongService(PlaylistSongRepository playlistSongRepository, PlaylistRepository playlistRepository, SongRepository songRepository, StorageService storageService) {
        this.playlistSongRepository = playlistSongRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.storageService = storageService;
    }

    public List<PlaylistSongDTO> searchPlaylistSongs(PlaylistSongCriteria criteria) {
        Specification<PlaylistSongEntity> spec = playlistSongSpecification(criteria);
        List<PlaylistSongEntity> playlistSongs = playlistSongRepository.findAll(spec);
        return playlistSongs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PlaylistSongDTO> getSongsByPlaylistId(Long playlistId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        List<PlaylistSongEntity> playlistSongs = playlistSongRepository.findByPlaylist(playlist);
        return playlistSongs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PlaylistSongDTO addSongToPlaylist(Long playlistId, Long songId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        playlistSongRepository.findByPlaylistAndSong(playlist, song)
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Song already exists in the playlist");
                });

        PlaylistSongEntity playlistSong = new PlaylistSongEntity();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);

        PlaylistSongEntity savedPlaylistSong = playlistSongRepository.save(playlistSong);
        return toDto(savedPlaylistSong);
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        PlaylistSongEntity playlistSong = playlistSongRepository.findByPlaylistAndSong(playlist, song)
                .orElseThrow(() -> new IllegalArgumentException("Song not found in the playlist"));

        playlistSongRepository.delete(playlistSong);
    }

    public static Specification<PlaylistSongEntity> playlistSongSpecification(PlaylistSongCriteria criteria) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Filter by playlist ID
            if (criteria.getPlaylistId() != null) {
                LongFilter playlistIdFilter = criteria.getPlaylistId();
                if (playlistIdFilter.getEquals() != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("playlist").get("playlistID"), playlistIdFilter.getEquals()));
                }
            }

            // Filter by song title
            if (criteria.getSongTitle() != null) {
                StringFilter songTitleFilter = criteria.getSongTitle();
                if (songTitleFilter.getContains() != null) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(root.get("song").get("title")), "%" + songTitleFilter.getContains().toLowerCase() + "%"));
                }
            }

            // Filter by album title
            if (criteria.getAlbumTitle() != null) {
                StringFilter albumTitleFilter = criteria.getAlbumTitle();
                // Use root.join to join the "song" relationship
                Join<PlaylistSongEntity, SongEntity> songJoin = root.join("song", JoinType.LEFT);
                Join<SongEntity, AlbumEntity> albumJoin = songJoin.join("album", JoinType.LEFT);
                if (albumTitleFilter.getContains() != null) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(albumJoin.get("title")), "%" + albumTitleFilter.getContains().toLowerCase() + "%"));
                }
            }

            // Filter by artist name
            if (criteria.getArtistName() != null) {
                StringFilter artistNameFilter = criteria.getArtistName();
                // Use root.join to join the "song" relationship
                Join<PlaylistSongEntity, SongEntity> songJoin = root.join("song", JoinType.LEFT);
                Join<SongEntity, ArtistEntity> artistJoin = songJoin.join("artist", JoinType.LEFT);
                if (artistNameFilter.getContains() != null) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(artistJoin.get("name")), "%" + artistNameFilter.getContains().toLowerCase() + "%"));
                }
            }

            return predicate;
        };
    }

    private PlaylistSongDTO toDto(PlaylistSongEntity playlistSong) {
        SongEntity song = playlistSong.getSong();
        String signedCoverUrl = (song.getCoverImage() != null && !song.getCoverImage().isBlank())
                ? storageService.generateSignedUrl(song.getCoverImage())
                : null;
        String signedFilePath = (song.getFilePath() != null && !song.getFilePath().isBlank())
                ? storageService.generateSignedUrl(song.getFilePath())
                : null;

        return PlaylistSongDTO.entityToDTO(playlistSong, signedFilePath, signedCoverUrl);
    }
}
