package org.project.musicweb.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Listening_History")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", nullable = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "songID", nullable = false)
    private SongEntity song;

    @Temporal(TemporalType.TIMESTAMP)
    private Date listenedOn = new Date();

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
