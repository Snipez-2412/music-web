package org.project.musicweb.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Albums")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumID;

    @Column(nullable = false, length = 255)
    private String title;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artistID", nullable = false)
    private ArtistEntity artist;

    @Column(length = 500)
    private String coverImage;

    @Transient
    private String signedCoverUrl;

    public Long getAlbumID() {
        return albumID;
    }

    public void setAlbumID(Long albumID) {
        this.albumID = albumID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSignedCoverUrl() {
        return signedCoverUrl;
    }

    public void setSignedCoverUrl(String signedCoverUrl) {
        this.signedCoverUrl = signedCoverUrl;
    }
}
