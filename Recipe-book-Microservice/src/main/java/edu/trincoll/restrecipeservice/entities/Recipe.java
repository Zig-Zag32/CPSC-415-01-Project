package edu.trincoll.restrecipeservice.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Transient
    List<Ingredient> ingredientList = new ArrayList<>();

    String name;

    @Transient
    List<String> steps = new ArrayList<>();

    // New field to store the string value of the steps
    @Transient
    String stepsString;

    // New field to store the string value of the return value of the toString() method
    String recipeString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    // Getter and setter for stepsString
    public String getStepsString() {
        return stepsString;
    }

    public void setStepsString(String stepsString) {
        this.stepsString = stepsString;
    }

    // Getter and setter for recipeString
    public String getRecipeString() {
        return recipeString;
    }

    public void setRecipeString(String recipeString) {
        this.recipeString = recipeString;
    }

    // Override toString method to produce sentences
    @Override
    // Override toString method to produce sentences
    @Override
    public String toString() {
        // If recipeString is not already set, generate it
        if (recipeString == null) {
            StringBuilder recipeStringBuilder = new StringBuilder();
            recipeStringBuilder.append("Recipe: ").append(name).append("\n");

            // Iterate over ingredientList and append each ingredient to the string
            for (Ingredient ingredient : ingredientList) {
                recipeStringBuilder.append("- ").append(ingredient.getAmount()).append(" ")
                        .append(ingredient.getUnit()).append(" of ").append(ingredient.getName()).append("\n");
            }

            // Append steps to the string
            recipeStringBuilder.append("Steps:\n");
            for (int i = 0; i < steps.size(); i++) {
                recipeStringBuilder.append((i + 1)).append(". ").append(steps.get(i)).append("\n");
            }

            // Set recipeString
            recipeString = recipeStringBuilder.toString();

            // Set stepsString
            stepsString = recipeStringBuilder.toString(); // Update stepsString
        }

        return recipeString;
    }

}
