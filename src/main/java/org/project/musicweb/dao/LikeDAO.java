package org.project.musicweb.dao;

import org.project.musicweb.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeDAO extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByUserID(Long userID);
}
