package org.project.musicweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
