package org.project.musicweb.repository;

import org.project.musicweb.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long>, JpaSpecificationExecutor<PlaylistEntity> {
    List<PlaylistEntity> findByUserId(Long userId);


}
