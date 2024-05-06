package edu.trincoll.restrecipeservice.controllers;

import edu.trincoll.restrecipeservice.dao.RecipeRepository;
import edu.trincoll.restrecipeservice.entities.Ingredient;
import edu.trincoll.restrecipeservice.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RecipeRestController
{
    private final RecipeRepository repository;

    @Autowired
    public RecipeRestController(RecipeRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes()
    {
        return repository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipesById(@PathVariable String id)
    {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Recipe> deleteRecipeById(@PathVariable String id)
    {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe)
    {
        repository.save(recipe);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(recipe.getName())
                .toUri();
        return ResponseEntity.created(location).body(recipe);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String id, @RequestBody Recipe recipeUpdate) {

        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Kitchen Item not found with id " + id));
        recipe.setSteps(recipeUpdate.getSteps());
        recipe.setIngredientList(recipeUpdate.getIngredientList());
        recipe.setName(recipeUpdate.getName());
        Recipe updatedRecipe = repository.save(recipe);

        return ResponseEntity.ok(updatedRecipe);
    }
}
