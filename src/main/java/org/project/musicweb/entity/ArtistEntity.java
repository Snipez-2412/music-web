package org.project.musicweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Artists")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistID;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 1000)
    private String profilePic;

    @Transient
    private String signedProfileUrl;

    @Column(length = 100)
    private String country;

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

    public String getSignedProfileUrl() {
        return signedProfileUrl;
    }

    public void setSignedProfileUrl(String signedProfileUrl) {
        this.signedProfileUrl = signedProfileUrl;
    }
}
