package org.project.musicweb.repository;

import org.project.musicweb.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    List<AlbumEntity> findByUserID(Long userID);
}
