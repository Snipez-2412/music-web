package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.HistoryEntity;
import java.util.Date;

@Data
public class HistoryDTO {
    private Long historyID;
    private Long userID;
    private Long songID;
    private Long albumID;
    private Long artistID;
    private String songTitle;
    private String albumTitle;
    private String artistName;
    private String genre;
    private String signedCoverUrl;
    private String signedProfileUrl;
    private Date listenedOn = new Date();

    // Mapper
    public static HistoryDTO entityToDTO (HistoryEntity history, String signedCoverUrl, String signedProfileUrl) {
        HistoryDTO dto  = new HistoryDTO();
        dto.setHistoryID(history.getHistoryID());
        dto.setUserID(history.getUser().getId());
        dto.setSongID(history.getSong().getSongID());
        dto.setAlbumID(history.getAlbum().getAlbumID());
        dto.setArtistID(history.getArtist().getArtistID());
        dto.setSongTitle(history.getSong().getTitle());
        dto.setAlbumTitle(history.getAlbum().getTitle());
        dto.setArtistName(history.getArtist().getName());
        dto.setGenre(history.getSong().getGenre());
        dto.setListenedOn(history.getListenedOn());
        dto.setSignedCoverUrl(signedCoverUrl);
        dto.setSignedProfileUrl(signedProfileUrl);
        return dto;
    }
}
