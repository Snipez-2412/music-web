package org.project.musicweb.dto;

import org.project.musicweb.entity.SongEntity;

public class LyricsDTO {
    private Long lyricID;
    private SongEntity song;
    private String content;

    public LyricsDTO(Long lyricID, SongEntity song, String content) {
        this.lyricID = lyricID;
        this.song = song;
        this.content = content;
    }

    public Long getLyricID() {
        return lyricID;
    }

    public void setLyricID(Long lyricID) {
        this.lyricID = lyricID;
    }

    public SongEntity getSong() {
        return song;
    }

    public void setSong(SongEntity song) {
        this.song = song;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
