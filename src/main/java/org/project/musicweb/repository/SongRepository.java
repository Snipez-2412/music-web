package org.project.musicweb.repository;

import org.project.musicweb.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long>, JpaSpecificationExecutor<SongEntity> {

    Optional<SongEntity> findByTitleIgnoreCase(String title);
}
