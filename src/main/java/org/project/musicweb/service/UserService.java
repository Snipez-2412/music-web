package org.project.musicweb.service;

import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.repository.SongRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.*;

import java.util.List;

@Service
public class UserService {
    private final SongRepository songRepository;

    public UserService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongEntity> searchSongs(String title, String albumTitle, String artistName) {
        Specification<SongEntity> spec = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Always true initially

            if (title != null && !title.isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (albumTitle != null && !albumTitle.isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("album").get("title")), "%" + albumTitle.toLowerCase() + "%"));
            }

            if (artistName != null && !artistName.isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("album").get("artist").get("name")), "%" + artistName.toLowerCase() + "%"));
            }

            return predicate;
        };

        return songRepository.findAll(spec);
    }
}
