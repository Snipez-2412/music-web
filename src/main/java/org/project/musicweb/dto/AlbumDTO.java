package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.AlbumEntity;
import java.util.Date;

@Data
public class AlbumDTO {
    private Long albumID;
    private String title;
    private String genre;
    private Date releaseDate;
    private String artistName;
    private Long artistID;
    private String coverImage;
    private String signedCoverUrl;

    // Mapper
    public static AlbumDTO entityToDTO(AlbumEntity album, String signedCoverUrl) {
        AlbumDTO dto = new AlbumDTO();
        dto.setAlbumID(album.getAlbumID());
        dto.setTitle(album.getTitle());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setGenre(album.getGenre());
        dto.setArtistName(album.getArtist().getName());
        dto.setArtistID(album.getArtist().getArtistID());
        dto.setCoverImage(album.getCoverImage());
        dto.setSignedCoverUrl(signedCoverUrl);
        return dto;
    }
}
