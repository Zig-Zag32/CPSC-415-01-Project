package edu.trincoll.restrecipeservice.entities;

import edu.trincoll.restrecipeservice.entities.Ingredient;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String ingredientList;

    String name;

    String steps;

    public Recipe()
    {

    }

    public Recipe(String ingredientList, String name, String steps) {
        this.ingredientList = ingredientList;
        this.name = name;
        this.steps = steps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) &&
                Objects.equals(ingredientList, recipe.ingredientList) &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(steps, recipe.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredientList, name, steps);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", ingredientList='" + ingredientList + '\'' +
                ", name='" + name + '\'' +
                ", steps='" + steps + '\'' +
                '}';
    }
}