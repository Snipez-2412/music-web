package org.project.musicweb.dto;

import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.ArtistEntity;

public class SongDTO {
    private Long songID;
    private String title;
    private String duration;
    private String genre;
    private ArtistEntity artist;
    private AlbumEntity album;
    private String filePath;

    public SongDTO(Long id, String title, String duration, String genre, ArtistEntity artist, AlbumEntity album, String filePath) {
        this.songID = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.artist = artist;
        this.album = album;
        this.filePath = filePath;
    }

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
}
