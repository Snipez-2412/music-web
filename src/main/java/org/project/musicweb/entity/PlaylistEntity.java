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

    @Column(nullable = false)
    private String privacySetting;

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

    public String getPrivacySetting() {
        return privacySetting;
    }

    public void setPrivacySetting(String privacySetting) {
        this.privacySetting = privacySetting;
    }
}