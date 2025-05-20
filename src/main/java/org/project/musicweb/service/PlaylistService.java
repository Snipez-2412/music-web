package org.project.musicweb.service;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.PlaylistDTO;
import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.entity.PlaylistSongEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;
import org.project.musicweb.module.query.PlaylistCriteria;
import org.project.musicweb.repository.PlaylistRepository;
import org.project.musicweb.repository.PlaylistSongRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.repository.UserRepository;
import org.project.musicweb.util.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;

    public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository, PlaylistSongRepository playlistSongRepository, UserRepository userRepository, StorageService storageService) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    public List<PlaylistDTO> getAllPlaylists() {
        List<PlaylistEntity> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PlaylistDTO> getPlaylistsByUserId(Long userId) {
        List<PlaylistEntity> playlists = playlistRepository.findByUserId(userId);
        return playlists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PlaylistDTO getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public List<PlaylistDTO> searchPlaylists(PlaylistCriteria criteria) {
        SpecificationUtils<PlaylistEntity> builder = new SpecificationUtils<>();
        Specification<PlaylistEntity> spec = builder.buildSpecification(criteria);
        List<PlaylistEntity> playlists = playlistRepository.findAll(spec);
        return playlists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PlaylistDTO addPlaylist(PlaylistDTO playlistDTO, MultipartFile imageFile) throws IOException {
        String imageFileName = imageFile != null ? storageService.uploadFile(imageFile) : null;

        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setName(playlistDTO.getName());
        playlist.setDescription(playlistDTO.getDescription());
        playlist.setCoverImage(imageFileName);

        // 🧩 Gán user
        Long userId = playlistDTO.getCreatedByUserID();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        playlist.setUser(user);

        PlaylistEntity saved = playlistRepository.save(playlist);

        if (playlistDTO.getSongIds() != null && !playlistDTO.getSongIds().isEmpty()) {
            addSongsToPlaylist(saved, playlistDTO.getSongIds());
        }

        return toDto(saved);
    }


    private void addSongsToPlaylist(PlaylistEntity playlist, List<Long> songIds) {
        List<SongEntity> songs = songRepository.findAllById(songIds);
        List<PlaylistSongEntity> playlistSongs = songs.stream()
                .map(song -> new PlaylistSongEntity(playlist, song))
                .collect(Collectors.toList());

        playlistSongRepository.saveAll(playlistSongs);
    }

    public PlaylistDTO updatePlaylist(Long id, PlaylistDTO playlistDTO, MultipartFile imageFile) throws IOException {
        PlaylistEntity existingPlaylist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        String imageFileName = imageFile != null ? storageService.uploadFile(imageFile) : null;
        existingPlaylist.setName(playlistDTO.getName());
        existingPlaylist.setDescription(playlistDTO.getDescription());
        existingPlaylist.setCoverImage(imageFileName);

        PlaylistEntity updated = playlistRepository.save(existingPlaylist);

        return toDto(updated);
    }


    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    private PlaylistDTO toDto(PlaylistEntity playlist) {
        String signedUrl = StringUtils.isNotBlank(playlist.getCoverImage())
                ? storageService.generateSignedUrl(playlist.getCoverImage()) : null;
        return PlaylistDTO.entityToDTO(playlist, signedUrl);
    }


}
