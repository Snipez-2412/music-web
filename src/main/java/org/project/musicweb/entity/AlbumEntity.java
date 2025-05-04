package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Albums")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumID;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 100)
    private String genre;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToOne
    @JoinColumn(name = "artistID", nullable = false)
    private ArtistEntity artist;

    @Column()
    private String coverImage;
}
