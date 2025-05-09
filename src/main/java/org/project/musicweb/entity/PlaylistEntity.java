package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Playlists")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistID;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlaylistSongEntity> playlistSongs = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(length = 1000)
    private String coverImage;

    @Column(nullable = true)
    private String description;
}