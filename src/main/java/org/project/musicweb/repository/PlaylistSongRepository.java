package org.project.musicweb.repository;

import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.entity.PlaylistSongEntity;
import org.project.musicweb.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, Long>, JpaSpecificationExecutor<PlaylistSongEntity> {
    List<PlaylistSongEntity> findBySong(SongEntity song);

    List<PlaylistSongEntity> findByPlaylist(PlaylistEntity playlist);

    Optional<PlaylistSongEntity> findByPlaylistAndSong(PlaylistEntity playlist, SongEntity song);
}

