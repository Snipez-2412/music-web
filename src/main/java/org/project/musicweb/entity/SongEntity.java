package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Songs")
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songID;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = true)
    private String duration;

    @Column(length = 100)
    private String genre;

    @ManyToOne
    @JoinColumn(name = "artistID", nullable = false)
    private ArtistEntity artist;

    @ManyToOne
    @JoinColumn(name = "albumID")
    private AlbumEntity album;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column()
    private String coverImage;
}
