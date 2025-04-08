package org.project.musicweb.service;

import org.project.musicweb.entity.HistoryEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.entity.AlbumEntity;
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

    public List<SongEntity> getRecentPlayedSongs(Long userID, int limit) {
        List<HistoryEntity> history = historyRepository.findTop10ByUser_UserIDOrderByListenedOnDesc(userID, PageRequest.of(0, limit));
        return history.stream()
                .map(HistoryEntity::getSong)
                .collect(Collectors.toList());
    }

    public List<ArtistEntity> getRecentPlayedArtists(Long userID, int limit) {
        List<HistoryEntity> history = historyRepository.findTop10ByUser_UserIDOrderByListenedOnDesc(userID, PageRequest.of(0, limit));
        return history.stream()
                .map(historyEntity -> historyEntity.getSong().getArtist())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<AlbumEntity> getRecentPlayedAlbums(Long userID, int limit) {
        List<HistoryEntity> history = historyRepository.findTop10ByUser_UserIDOrderByListenedOnDesc(userID, PageRequest.of(0, limit));
        return history.stream()
                .map(HistoryEntity::getSong)
                .map(SongEntity::getAlbum)
                .distinct()
                .collect(Collectors.toList());
    }
}
