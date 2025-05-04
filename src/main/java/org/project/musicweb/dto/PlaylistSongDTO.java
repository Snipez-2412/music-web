package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.PlaylistSongEntity;

@Data
public class PlaylistSongDTO {
    private Long id;
    private SongDTO song;

    public static PlaylistSongDTO entityToDTO(PlaylistSongEntity entity) {
        PlaylistSongDTO dto = new PlaylistSongDTO();
        dto.setId(entity.getId());

        // Map song to SongDTO
        SongDTO songDTO = new SongDTO();
        songDTO.setSongID(entity.getSong().getSongID());
        songDTO.setTitle(entity.getSong().getTitle());
        songDTO.setArtistID(entity.getSong().getArtist().getArtistID());
        songDTO.setDuration(entity.getSong().getDuration());
        songDTO.setSignedFilePath(entity.getSong().getFilePath()); // nếu có

        dto.setSong(songDTO);
        return dto;
    }
}
