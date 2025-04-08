package org.project.musicweb.service;

import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.entity.PlaylistSongEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.PlaylistSongCriteria;
import org.project.musicweb.repository.PlaylistRepository;
import org.project.musicweb.repository.PlaylistSongRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistSongService(PlaylistSongRepository playlistSongRepository, PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistSongRepository = playlistSongRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    public List<PlaylistSongEntity> searchPlaylistSongs(PlaylistSongCriteria criteria) {
        SpecificationUtils<PlaylistSongEntity> builder = new SpecificationUtils<>();
        Specification<PlaylistSongEntity> spec = builder.playlistSongSpecification(criteria);
        return playlistSongRepository.findAll(spec);
    }

    public List<PlaylistSongEntity> getSongsByPlaylistId(Long playlistId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        return playlistSongRepository.findByPlaylist(playlist);
    }

    public PlaylistSongEntity addSongToPlaylist(Long playlistId, Long songId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        // Check if song is already in the playlist
        playlistSongRepository.findByPlaylistAndSong(playlist, song)
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Song already exists in the playlist");
                });

        PlaylistSongEntity playlistSong = new PlaylistSongEntity();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);

        return playlistSongRepository.save(playlistSong);
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
}
