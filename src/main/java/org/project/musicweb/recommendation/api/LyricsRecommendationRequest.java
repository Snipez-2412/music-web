package org.project.musicweb.recommendation.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LyricsRecommendationRequest {
    private String lyrics;
    private int top_k;
}

