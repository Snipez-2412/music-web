package org.project.musicweb.dto;

import org.project.musicweb.entity.PlaylistEntity;
import org.project.musicweb.entity.SongEntity;

public class PlaylistSongDTO {
    private Long id;
    private PlaylistEntity playlist;
    private SongEntity song;

    public PlaylistSongDTO(Long id, PlaylistEntity playlist, SongEntity song) {
        this.id = id;
        this.playlist = playlist;
        this.song = song;
    }

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
