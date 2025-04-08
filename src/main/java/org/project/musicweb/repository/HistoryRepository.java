package org.project.musicweb.repository;

import org.project.musicweb.entity.HistoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findTop10ByUser_UserIDOrderByListenedOnDesc(Long userID, Pageable pageable);
}
