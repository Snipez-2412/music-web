package org.project.musicweb.recommendation.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequest {
    private String description;
    private int top_k;
}

