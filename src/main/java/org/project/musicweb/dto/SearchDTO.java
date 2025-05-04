package org.project.musicweb.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String type;
    private Long id;
    private String title;
    private String signedCoverUrl;

    private Long artistID;
    private String artistName;
    private String signedProfileUrl;

    public SearchDTO(String type, Long id, String title, String signedCoverUrl) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.signedCoverUrl = signedCoverUrl;
    }

    public SearchDTO(Long artistID, String artistName, String signedProfileUrl) {
        this.artistID = artistID;
      this.artistName = artistName;
        this.signedProfileUrl = signedProfileUrl;
    }
}
