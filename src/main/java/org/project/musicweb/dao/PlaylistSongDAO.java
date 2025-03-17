package org.project.musicweb.dao;

import org.project.musicweb.entity.PlaylistSongEntity;
import org.project.musicweb.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistSongDAO extends JpaRepository<PlaylistSongEntity, Long> {
    List<PlaylistSongEntity> findBySong(SongEntity song);
}
