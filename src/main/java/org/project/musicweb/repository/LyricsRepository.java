package org.project.musicweb.repository;

import org.project.musicweb.entity.LyricsEntity;
import org.project.musicweb.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LyricsRepository extends JpaRepository<LyricsEntity, Long> {
    List<LyricsEntity> findByLyricID(Long lyricID);

    Optional<LyricsEntity> findBySong(SongEntity song);

    Optional<LyricsEntity> findBySong_SongID(Long songId);
}
