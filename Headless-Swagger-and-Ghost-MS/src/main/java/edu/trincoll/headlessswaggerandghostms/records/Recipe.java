package edu.trincoll.headlessswaggerandghostms.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Recipe(String ingredientList, String name, String steps) {
}
