package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(length = 1000)
    private String coverImage;

    @Column(nullable = true)
    private String description;
}