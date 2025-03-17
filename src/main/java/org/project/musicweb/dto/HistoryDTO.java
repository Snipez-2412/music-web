package org.project.musicweb.dto;

import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;

import java.util.Date;

public class HistoryDTO {
    private Long historyID;
    private UserEntity user;
    private SongEntity song;
    private Date listenedOn = new Date();

    public HistoryDTO(long historyID, UserEntity user, SongEntity song, Date listenedOn) {
        this.historyID = historyID;
        this.user = user;
        this.song = song;
        this.listenedOn = listenedOn;
    }

    public Long getHistoryID() {
        return historyID;
    }

    public void setHistoryID(Long historyID) {
        this.historyID = historyID;
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

    public Date getListenedOn() {
        return listenedOn;
    }

    public void setListenedOn(Date listenedOn) {
        this.listenedOn = listenedOn;
    }
}
