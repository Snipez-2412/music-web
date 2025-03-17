package org.project.musicweb.dao;

import org.project.musicweb.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumDAO extends JpaRepository<AlbumEntity, Long> {
    List<AlbumEntity> findByUserID(Long userID);
}
