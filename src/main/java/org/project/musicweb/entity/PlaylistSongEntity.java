package org.project.musicweb.entity;

import jakarta.persistence.*;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlaylistEntity getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistEntity playlist) {
        this.playlist = playlist;
    }

    public SongEntity getSong() {
        return song;
    }

    public void setSong(SongEntity song) {
        this.song = song;
    }
}
