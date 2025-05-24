package org.project.musicweb.recommendation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationResult {
    @JsonProperty("model")
    private String model;

    @JsonProperty("song")
    private String song;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("score")
    private double score;
}
