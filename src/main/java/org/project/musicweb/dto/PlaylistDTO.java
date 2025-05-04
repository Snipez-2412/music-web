package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.PlaylistEntity;

import java.util.Date;
import java.util.List;

@Data
public class PlaylistDTO {
    private Long playlistID;
    private List<Long> songIds;
    private String name;
    private String coverImage;
    private String signedCoverUrl;
    private String description;
    private Date creationDate;
    private Long createdByUserID;
    private String createdByUsername;

    // Mapper
    public static PlaylistDTO entityToDTO(PlaylistEntity playlist, String signedCoverUrl) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setPlaylistID(playlist.getPlaylistID());
        dto.setName(playlist.getName());
        dto.setCoverImage(playlist.getCoverImage());
        dto.setSignedCoverUrl(signedCoverUrl);
        dto.setDescription(playlist.getDescription());
        dto.setCreationDate(playlist.getCreationDate());
        dto.setCreatedByUserID(playlist.getUser().getId());
        dto.setCreatedByUsername(playlist.getUser().getUsername());
        return dto;
    }
}
