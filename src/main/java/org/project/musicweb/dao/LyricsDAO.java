package org.project.musicweb.dao;

import org.project.musicweb.entity.LyricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LyricsDAO extends JpaRepository<LyricsEntity, Long> {
    List<LyricsEntity> findByUserID(Long userID);
}
