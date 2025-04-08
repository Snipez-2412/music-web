package org.project.musicweb.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Playlists")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistID;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(length = 1000)
    private String coverImage;

    @Transient
    private String signedCoverUrl;

    @Column(nullable = true)
    private String description;

    public Long getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(Long playlistID) {
        this.playlistID = playlistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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