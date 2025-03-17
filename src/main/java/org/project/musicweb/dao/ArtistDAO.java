package org.project.musicweb.dao;

import org.project.musicweb.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistDAO extends JpaRepository<ArtistEntity, Long> {
    List<ArtistEntity> findByUserID(Long userID);
}
