package org.project.musicweb.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", nullable = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "songID", nullable = false)
    private SongEntity song;

    @Temporal(TemporalType.TIMESTAMP)
    private Date likedOn = new Date();

    public Long getLikeID() {
        return likeID;
    }

    public void setLikeID(Long likeID) {
        this.likeID = likeID;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SongEntity getSong() {
        return song;
    }

    public void setSong(SongEntity song) {
        this.song = song;
    }

    public Date getLikedOn() {
        return likedOn;
    }

    public void setLikedOn(Date likedOn) {
        this.likedOn = likedOn;
    }

}
