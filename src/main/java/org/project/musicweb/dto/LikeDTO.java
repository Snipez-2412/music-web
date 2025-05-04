package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.LikeEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;

import java.util.Date;

@Data
public class LikeDTO {
    private Long likeID;
    private UserEntity user;
    private SongEntity song;
    private Date likedOn;

    // Mapper
    public static LikeDTO entityToDTO(LikeEntity like) {
        LikeDTO dto = new LikeDTO();
        dto.setLikeID(like.getLikeID());
        dto.setUser(like.getUser());
        dto.setSong(like.getSong());
        dto.setLikedOn(like.getLikedOn());
        return dto;
    }
}
