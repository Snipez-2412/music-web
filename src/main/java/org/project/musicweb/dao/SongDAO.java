package org.project.musicweb.dao;

import org.project.musicweb.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongDAO extends JpaRepository<SongEntity, Long> {
    List<SongEntity> findByUserID(Long userID);
}
