package org.project.musicweb.dto;

import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;

import java.util.Date;

public class LikeDTO {
    private Long likeID;
    private UserEntity user;
    private SongEntity song;
    private Date likedOn = new Date();
    private String coverImage;

    public LikeDTO(Long likeID, UserEntity user, SongEntity song, Date likedOn) {
        this.likeID = likeID;
        this.user = user;
        this.song = song;
        this.likedOn = likedOn;
        this.coverImage = null;
    }

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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
