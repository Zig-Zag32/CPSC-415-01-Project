package edu.trincoll.headlessswaggerandghostms.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeRecommendationResponse(String text) {

}
