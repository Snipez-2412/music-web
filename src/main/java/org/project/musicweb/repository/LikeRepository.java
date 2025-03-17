package org.project.musicweb.repository;

import org.project.musicweb.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByUserID(Long userID);
}
