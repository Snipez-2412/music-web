package org.project.musicweb.service;

import org.project.musicweb.dto.LyricsDTO;
import org.project.musicweb.entity.LyricsEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.repository.LyricsRepository;
import org.project.musicweb.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LyricsService {
    private final LyricsRepository lyricsRepository;
    private final SongRepository songRepository;

    public LyricsService(LyricsRepository lyricsRepository, SongRepository songRepository) {
        this.lyricsRepository = lyricsRepository;
        this.songRepository = songRepository;
    }

    public LyricsDTO addLyrics(Long songId, String content) {
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        if (lyricsRepository.findBySong(song).isPresent()) {
            throw new IllegalStateException("Lyrics already exist for this song");
        }

        LyricsEntity lyrics = new LyricsEntity();
        lyrics.setSong(song);
        lyrics.setContent(content);
        LyricsEntity savedLyrics = lyricsRepository.save(lyrics);

        return LyricsDTO.entityToDTO(savedLyrics);
    }

    public LyricsDTO updateLyrics(Long songId, String newContent) {
        LyricsEntity lyrics = lyricsRepository.findBySongSongID(songId)
                .orElseThrow(() -> new IllegalArgumentException("Lyrics not found for this song"));

        lyrics.setContent(newContent);
        LyricsEntity updatedLyrics = lyricsRepository.save(lyrics);

        return LyricsDTO.entityToDTO(updatedLyrics);
    }

    public void deleteLyrics(Long songId) {
        LyricsEntity lyrics = lyricsRepository.findBySongSongID(songId)
                .orElseThrow(() -> new IllegalArgumentException("Lyrics not found for this song"));

        lyricsRepository.delete(lyrics);
    }

    public Optional<LyricsDTO> getLyricsBySongId(Long songId) {
        Optional<LyricsEntity> lyricsEntity = lyricsRepository.findBySongSongID(songId);
        return lyricsEntity.map(LyricsDTO::entityToDTO);
    }
}
