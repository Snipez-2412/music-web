package org.project.musicweb.dao;

import org.project.musicweb.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryDAO extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findByUserID(Long userID);
}
