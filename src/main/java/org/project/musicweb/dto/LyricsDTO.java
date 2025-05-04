package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.LyricsEntity;
import org.project.musicweb.entity.SongEntity;

@Data
public class LyricsDTO {
    private Long lyricID;
    private SongEntity song;
    private String content;

    public static LyricsDTO entityToDTO(LyricsEntity lyrics) {
        LyricsDTO dto = new LyricsDTO();
        dto.setLyricID(lyrics.getLyricID());
        dto.setSong(lyrics.getSong());
        dto.setContent(lyrics.getContent());
        return dto;
    }
}
