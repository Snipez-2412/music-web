package org.project.musicweb.repository;

import org.project.musicweb.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    List<ArtistEntity> findByUserID(Long userID);
}
