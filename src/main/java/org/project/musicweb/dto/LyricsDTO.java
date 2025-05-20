package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.LyricsEntity;

@Data
public class LyricsDTO {
    private Long lyricID;
    private Long songID;
    private String content;

    public static LyricsDTO entityToDTO(LyricsEntity lyrics) {
        LyricsDTO dto = new LyricsDTO();
        dto.setLyricID(lyrics.getLyricID());
        dto.setSongID(lyrics.getSong().getSongID());
        dto.setContent(lyrics.getContent());
        return dto;
    }
}
