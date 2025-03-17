package org.project.musicweb.dto;

public class ArtistDTO {
    private Long artistID;
    private String name;
    private String bio;
    private String profilePic;
    private String country;

    public ArtistDTO(long artistID, String name, String bio, String profilePic, String country) {
        this.artistID = artistID;
        this.name = name;
        this.bio = bio;
        this.profilePic = profilePic;
        this.country = country;
    }

    public Long getArtistID() {
        return artistID;
    }

    public void setArtistID(Long artistID) {
        this.artistID = artistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
