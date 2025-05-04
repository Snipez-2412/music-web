package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.ArtistEntity;

@Data
public class ArtistDTO {
    private Long artistID;
    private String name;
    private String bio;
    private String profilePic;
    private String signedProfileUrl;
    private String country;

    // Mapper
    public static ArtistDTO entityToDto(ArtistEntity artist, String signedProfileUrl) {
        ArtistDTO dto = new ArtistDTO();
        dto.setArtistID(artist.getArtistID());
        dto.setName(artist.getName());
        dto.setBio(artist.getBio());
        dto.setProfilePic(artist.getProfilePic());
        dto.setSignedProfileUrl(signedProfileUrl);
        return dto;
    }
}
