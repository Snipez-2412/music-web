package org.project.musicweb.dto;

import org.project.musicweb.entity.UserEntity;

import java.util.Date;

public class SubscriptionDTO {
    private Long subscriptionID;
    private UserEntity user;
    private String type;
    private Date startDate;
    private Date endDate;
    private Double price;

    public SubscriptionDTO(Long subscriptionID, UserEntity user, String type, Date startDate, Date endDate, Double price) {
        this.subscriptionID = subscriptionID;
        this.user = user;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Long getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(Long subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
