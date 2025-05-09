package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.LikeEntity;
import org.project.musicweb.entity.UserEntity;

import java.util.Date;

@Data
public class LikeDTO {
    private Long likeID;
    private Long userID;
    private Long songID;
    private Date likedOn;

    public static LikeDTO entityToDTO(LikeEntity like) {
        LikeDTO dto = new LikeDTO();
        dto.setLikeID(like.getLikeID());
        dto.setUserID(like.getUser().getId());
        dto.setSongID(like.getSong().getSongID());
        dto.setLikedOn(like.getLikedOn());
        return dto;
    }
}

