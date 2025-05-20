package org.project.musicweb.recommendation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationResult {
    @JsonProperty("Song")
    private String song;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("similarity_score")
    private double similarity_score;
}
