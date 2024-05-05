package edu.trincoll.headlessswaggerandghostms.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpoonacularRecipeData(String title, String image, Integer servings, Integer readyInMinutes, String summary, List<KitchenItem> ingredients, String instructions) {
}
