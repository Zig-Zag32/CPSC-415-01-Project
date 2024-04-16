


import edu.trincoll.restrecipeservice.dao.RecipeRepository;
import edu.trincoll.restrecipeservice.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RecipeRestController {

    private final RecipeRepository repository;

    @Autowired
    public RecipeRestController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Recipe> deleteRecipeById(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        repository.save(recipe);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(recipe.getId())
                .toUri();
        return ResponseEntity.created(location).body(recipe);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {

        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found with id " + id));
        deleteRecipeById(id);

        Recipe savedRecipe = repository.save(recipe);

        return ResponseEntity.ok(savedRecipe);
    }
}