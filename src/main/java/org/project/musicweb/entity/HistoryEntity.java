package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Listening_History")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "songID")
    private SongEntity song;

    @ManyToOne
    @JoinColumn(name = "albumID")
    private AlbumEntity album;

    @ManyToOne
    @JoinColumn(name = "artistID")
    private ArtistEntity artist;

    @Temporal(TemporalType.TIMESTAMP)
    private Date listenedOn = new Date();
}
