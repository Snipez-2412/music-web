package org.project.musicweb.entity;

import jakarta.persistence.*;

@Entity
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artistID", nullable = false)
    private ArtistEntity artist;

    @ManyToOne
    @JoinColumn(name = "albumID")
    private AlbumEntity album;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Transient
    private String signedFilePath;

    public Long getSongID() {
        return songID;
    }

    public void setSongID(Long songID) {
        this.songID = songID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSignedFilePath() {
        return signedFilePath;
    }

    public void setSignedFilePath(String signedFilePath) {
        this.signedFilePath = signedFilePath;
    }
}
