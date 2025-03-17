package org.project.musicweb.repository;

import org.project.musicweb.entity.PlaylistSongEntity;
import org.project.musicweb.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, Long> {
    List<PlaylistSongEntity> findBySong(SongEntity song);
}
