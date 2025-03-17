package org.project.musicweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Lyrics")
public class LyricsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lyricID;

    @OneToOne
    @JoinColumn(name = "songID", nullable = false)
    private SongEntity song;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

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
