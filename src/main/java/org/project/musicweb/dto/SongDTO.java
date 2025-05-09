package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.SongEntity;

@Data
public class SongDTO {
    private Long songID;
    private String title;
    private String duration;
    private String genre;
    private String coverImage;
    private String filePath;
    private String signedFilePath;
    private String signedCoverUrl;
    private Long artistID;
    private String artistName;
    private Long albumID;
    private String albumTitle;

    // Mapper
    public static SongDTO entityToDto (SongEntity song, String signedFilePath, String signedCoverUrl) {
        SongDTO dto = new SongDTO();
        dto.setSongID(song.getSongID());
        dto.setTitle(song.getTitle());
        dto.setDuration(song.getDuration());
        dto.setGenre(song.getGenre());
        dto.setCoverImage(song.getCoverImage());
        dto.setFilePath(song.getFilePath());
        dto.setSignedFilePath(signedFilePath);
        dto.setSignedCoverUrl(signedCoverUrl);
        dto.setArtistID(song.getArtist().getArtistID());
        dto.setArtistName(song.getArtist().getName());
        dto.setAlbumID(song.getAlbum().getAlbumID());
        dto.setAlbumTitle(song.getAlbum().getTitle());
        return dto;
    }
}
