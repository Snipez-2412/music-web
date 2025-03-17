package org.project.musicweb.dto;

import org.project.musicweb.entity.SubscriptionEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaylistDTO {
    private Long userID;
    private String username;
    private String email;
    private String password;
    private String subscriptionType;
    private Date joinDate = new Date();
    private List<SubscriptionEntity> subscriptions;

    public PlaylistDTO(Long userID, String username, String email, String password, String subscriptionType) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.subscriptionType = subscriptionType;
        this.joinDate = new Date();
        this.subscriptions = new ArrayList<SubscriptionEntity>();
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public List<SubscriptionEntity> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionEntity> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
