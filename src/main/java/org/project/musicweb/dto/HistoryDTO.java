package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.HistoryEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;
import java.util.Date;

@Data
public class HistoryDTO {
    private Long historyID;
    private UserEntity user;
    private SongEntity song;
    private Date listenedOn = new Date();

    // Mapper
    public static HistoryDTO entityToDTO (HistoryEntity history) {
        HistoryDTO dto  = new HistoryDTO();
        dto.setHistoryID(history.getHistoryID());
        dto.setUser(history.getUser());
        dto.setSong(history.getSong());
        dto.setListenedOn(history.getListenedOn());
        return dto;
    }
}
