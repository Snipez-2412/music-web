package org.project.musicweb.util;

import jakarta.persistence.criteria.*;
import org.project.musicweb.common.filter.DateFilter;
import org.project.musicweb.common.filter.LongFilter;
import org.project.musicweb.common.filter.StringFilter;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.entity.PlaylistSongEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.module.query.PlaylistSongCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.Date;

public class SpecificationUtils<T> {

    public Specification<T> buildSpecification(Object criteriaObject) {
        Specification<T> spec = Specification.where(null);

        for (Field field : criteriaObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object filterValue = field.get(criteriaObject);

                if (filterValue == null) continue;

                String fieldName = field.getName();

                if (filterValue instanceof StringFilter stringFilter) {
                    spec = spec.and(stringFilterSpec(fieldName, stringFilter));
                } else if (filterValue instanceof LongFilter longFilter) {
                    spec = spec.and(longFilterSpec(fieldName, longFilter));
                } else if (filterValue instanceof DateFilter dateFilter) {
                    spec = spec.and(dateFilterSpec(fieldName, dateFilter));
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field in criteria", e);
            }
        }

        return spec;
    }

    private Specification<T> stringFilterSpec(String field, StringFilter filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter.getEquals() != null) {
                predicate = cb.equal(cb.lower(root.get(field)), filter.getEquals().toLowerCase());
            }
            if (filter.getContains() != null) {
                predicate = cb.like(cb.lower(root.get(field)), "%" + filter.getContains().toLowerCase() + "%");
            }
            return predicate;
        };
    }

    private Specification<T> longFilterSpec(String field, LongFilter filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getEquals() != null) {
                if ("albumId".equals(field)) {
                    Join<Object, Object> albumJoin = root.join("album", JoinType.LEFT);
                    predicate = cb.equal(albumJoin.get("albumID"), filter.getEquals());
                } else {
                    predicate = cb.equal(root.get(field), filter.getEquals());
                }
            }

            if (filter.getGreaterThan() != null) {
                if ("albumId".equals(field)) {
                    Join<Object, Object> albumJoin = root.join("album", JoinType.LEFT);
                    predicate = cb.and(predicate, cb.greaterThan(albumJoin.get("albumID"), filter.getGreaterThan()));
                } else {
                    predicate = cb.and(predicate, cb.greaterThan(root.get(field), filter.getGreaterThan()));
                }
            }

            if (filter.getLessThan() != null) {
                if ("albumId".equals(field)) {
                    Join<Object, Object> albumJoin = root.join("album", JoinType.LEFT);
                    predicate = cb.and(predicate, cb.lessThan(albumJoin.get("albumID"), filter.getLessThan()));
                } else {
                    predicate = cb.and(predicate, cb.lessThan(root.get(field), filter.getLessThan()));
                }
            }

            return predicate;
        };
    }


    private Specification<T> dateFilterSpec(String field, DateFilter filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter.getEquals() != null) {
                Date date = toDate(filter.getEquals());
                predicate = cb.equal(root.get(field), date);
            }
            if (filter.getBefore() != null) {
                predicate = cb.and(predicate, cb.lessThan(root.get(field), toDate(filter.getBefore())));
            }
            if (filter.getAfter() != null) {
                predicate = cb.and(predicate, cb.greaterThan(root.get(field), toDate(filter.getAfter())));
            }
            return predicate;
        };
    }

    private Date toDate(java.time.LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Specification<PlaylistSongEntity> playlistSongSpecification(PlaylistSongCriteria criteria) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Filter by playlist ID
            if (criteria.getPlaylistId() != null) {
                LongFilter playlistIdFilter = criteria.getPlaylistId();
                if (playlistIdFilter.getEquals() != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("playlist").get("playlistID"), playlistIdFilter.getEquals()));
                }
            }

            // Filter by song title
            if (criteria.getSongTitle() != null) {
                StringFilter songTitleFilter = criteria.getSongTitle();
                if (songTitleFilter.getContains() != null) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(root.get("song").get("title")), "%" + songTitleFilter.getContains().toLowerCase() + "%"));
                }
            }

            // Filter by album title
            if (criteria.getAlbumTitle() != null) {
                StringFilter albumTitleFilter = criteria.getAlbumTitle();
                // Use root.join to join the "song" relationship
                Join<PlaylistSongEntity, SongEntity> songJoin = root.join("song", JoinType.LEFT);
                Join<SongEntity, AlbumEntity> albumJoin = songJoin.join("album", JoinType.LEFT);
                if (albumTitleFilter.getContains() != null) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(albumJoin.get("title")), "%" + albumTitleFilter.getContains().toLowerCase() + "%"));
                }
            }

            // Filter by artist name
            if (criteria.getArtistName() != null) {
                StringFilter artistNameFilter = criteria.getArtistName();
                // Use root.join to join the "song" relationship
                Join<PlaylistSongEntity, SongEntity> songJoin = root.join("song", JoinType.LEFT);
                Join<SongEntity, ArtistEntity> artistJoin = songJoin.join("artist", JoinType.LEFT);
                if (artistNameFilter.getContains() != null) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(artistJoin.get("name")), "%" + artistNameFilter.getContains().toLowerCase() + "%"));
                }
            }

            return predicate;
        };
    }
}
