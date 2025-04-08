package org.project.musicweb.repository;

import org.project.musicweb.entity.LikeEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndSong(UserEntity user, SongEntity song);

    List<LikeEntity> findByUser_UserID(Long userId);
}
