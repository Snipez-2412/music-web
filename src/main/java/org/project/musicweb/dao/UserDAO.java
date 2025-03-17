package org.project.musicweb.dao;

import org.project.musicweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUserID(Long userID);
}
