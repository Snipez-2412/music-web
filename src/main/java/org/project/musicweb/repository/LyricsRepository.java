package org.project.musicweb.repository;

import org.project.musicweb.entity.LyricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LyricsRepository extends JpaRepository<LyricsEntity, Long> {
    List<LyricsEntity> findByUserID(Long userID);
}
