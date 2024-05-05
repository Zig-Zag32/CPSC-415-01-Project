package edu.trincoll.headlessswaggerandghostms.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpoonacularRecipeResponse(List<SpoonacularRecipeData> recipes) {
}
