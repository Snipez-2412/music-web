package org.project.musicweb.service;

import org.project.musicweb.dto.HistoryDTO;
import org.project.musicweb.entity.HistoryEntity;
import org.project.musicweb.repository.HistoryRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.repository.PlaylistRepository;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public HistoryService(
            HistoryRepository historyRepository,
            SongRepository songRepository,
            PlaylistRepository playlistRepository,
            ArtistRepository artistRepository,
            AlbumRepository albumRepository) {
        this.historyRepository = historyRepository;
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    public List<HistoryDTO> getRecentPlayedSongs(Long userID, int limit) {
        List<HistoryEntity> history = historyRepository.findTop10ByUserIdOrderByListenedOnDesc(userID, PageRequest.of(0, limit));
        return history.stream()
                .map(HistoryDTO::entityToDTO)
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> getRecentPlayedArtists(Long userID, int limit) {
        List<HistoryEntity> history = historyRepository.findTop10ByUserIdOrderByListenedOnDesc(userID, PageRequest.of(0, limit));
        return history.stream()
                .map(historyEntity -> {
                    HistoryDTO dto = HistoryDTO.entityToDTO(historyEntity);
                    dto.setSong(historyEntity.getSong());
                    return dto;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> getRecentPlayedAlbums(Long userID, int limit) {
        List<HistoryEntity> history = historyRepository.findTop10ByUserIdOrderByListenedOnDesc(userID, PageRequest.of(0, limit));
        return history.stream()
                .map(HistoryDTO::entityToDTO)
                .distinct()
                .collect(Collectors.toList());
    }
}
