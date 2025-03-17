package org.project.musicweb.dao;

import org.project.musicweb.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionDAO extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByUserID(Long userID);
}
