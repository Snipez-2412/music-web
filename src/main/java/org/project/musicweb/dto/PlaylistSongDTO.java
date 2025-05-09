package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.PlaylistSongEntity;

@Data
public class PlaylistSongDTO {
    private Long id;
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

    public static PlaylistSongDTO entityToDTO(PlaylistSongEntity playlistSong, String signedFilePath, String signedCoverUrl) {
        PlaylistSongDTO dto = new PlaylistSongDTO();
        dto.setId(playlistSong.getId());
        dto.setSongID(playlistSong.getSong().getSongID());
        dto.setTitle(playlistSong.getSong().getTitle());
        dto.setDuration(playlistSong.getSong().getDuration());
        dto.setGenre(playlistSong.getSong().getGenre());
        dto.setCoverImage(playlistSong.getSong().getCoverImage());
        dto.setFilePath(playlistSong.getSong().getFilePath());
        dto.setSignedFilePath(signedFilePath);
        dto.setSignedCoverUrl(signedCoverUrl);
        dto.setArtistID(playlistSong.getSong().getArtist().getArtistID());
        dto.setArtistName(playlistSong.getSong().getArtist().getName());

        if (playlistSong.getSong().getAlbum() != null) {
            dto.setAlbumID(playlistSong.getSong().getAlbum().getAlbumID());
            dto.setAlbumTitle(playlistSong.getSong().getAlbum().getTitle());
        }

        return dto;
    }
}
