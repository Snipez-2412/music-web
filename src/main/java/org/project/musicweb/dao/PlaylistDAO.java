package org.project.musicweb.dao;

import org.project.musicweb.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistDAO extends JpaRepository<PlaylistEntity, Long> {
    List<PlaylistEntity> findByUserID(Long userID);
}
