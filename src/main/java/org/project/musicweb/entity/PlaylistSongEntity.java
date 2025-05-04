package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Playlist_Songs")
public class PlaylistSongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlistID", nullable = false)
    private PlaylistEntity playlist;

    @ManyToOne
    @JoinColumn(name = "songID", nullable = false)
    private SongEntity song;

    public PlaylistSongEntity(PlaylistEntity playlist, SongEntity song) {
        this.playlist = playlist;
        this.song = song;
    }

    public PlaylistSongEntity() {

    }
}
