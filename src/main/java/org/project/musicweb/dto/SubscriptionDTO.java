package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.UserEntity;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SubscriptionDTO {
    private Long subscriptionID;
    private UserEntity user;
    private String type;
    private Date startDate;
    private Date endDate;
    private BigDecimal price;

    public SubscriptionDTO(Long subscriptionID,
                           UserEntity user,
                           String type,
                           Date startDate,
                           Date endDate,
                           BigDecimal price) {
        this.subscriptionID = subscriptionID;
        this.user = user;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
