package org.project.musicweb.service;

import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.AlbumDTO;
import org.project.musicweb.dto.HistoryDTO;
import org.project.musicweb.dto.UserDTO;
import org.project.musicweb.entity.*;
import org.project.musicweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;

    @Autowired
    public HistoryService(
            HistoryRepository historyRepository,
            SongRepository songRepository,
            UserRepository userRepository, StorageService storageService) {
        this.historyRepository = historyRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @Transactional
    public void addToHistory(Long userId, Long songId) {
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        AlbumEntity album = song.getAlbum();
        ArtistEntity artist = song.getArtist();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        HistoryEntity history = new HistoryEntity();
        history.setUser(user);
        history.setSong(song);
        history.setAlbum(album);
        history.setArtist(artist);
        history.setListenedOn(new java.util.Date());

        historyRepository.save(history);
    }

    public List<HistoryDTO> getRecentSongs(Long userId, int limit) {
        return historyRepository.findByUserIdOrderByListenedOnDesc(userId, PageRequest.of(0, limit * 3)).stream()
                .filter(h -> h.getSong() != null)
                .collect(Collectors.toMap(
                        h -> h.getSong().getSongID(),
                        h -> h,
                        (existing, replacement) -> existing // keep first (most recent)
                ))
                .values()
                .stream()
                .sorted((h1, h2) -> h2.getListenedOn().compareTo(h1.getListenedOn()))
                .limit(limit)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> getRecentAlbums(Long userId, int limit) {
        return historyRepository.findByUserIdAndAlbumIsNotNullOrderByListenedOnDesc(userId).stream()
                .filter(h -> h.getAlbum() != null)
                .collect(Collectors.toMap(
                        h -> h.getAlbum().getAlbumID(),
                        h -> h,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((h1, h2) -> h2.getListenedOn().compareTo(h1.getListenedOn()))
                .limit(limit)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> getRecentArtists(Long userId, int limit) {
        return historyRepository.findByUserIdAndArtistIsNotNullOrderByListenedOnDesc(userId).stream()
                .filter(h -> h.getArtist() != null)
                .collect(Collectors.toMap(
                        h -> h.getArtist().getArtistID(),
                        h -> h,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((h1, h2) -> h2.getListenedOn().compareTo(h1.getListenedOn()))
                .limit(limit)
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private HistoryDTO toDto(HistoryEntity history) {
        String coverUrl = null;
        if (history.getAlbum() != null && StringUtils.isNotBlank(history.getAlbum().getCoverImage())) {
            coverUrl = storageService.generateSignedUrl(history.getAlbum().getCoverImage());
        }
        String profileUrl = null;
        if (history.getArtist() != null && StringUtils.isNotBlank(history.getArtist().getProfilePic())) {
            profileUrl = storageService.generateSignedUrl(history.getArtist().getProfilePic());
        }
        return HistoryDTO.entityToDTO(history, coverUrl, profileUrl);
    }

}
