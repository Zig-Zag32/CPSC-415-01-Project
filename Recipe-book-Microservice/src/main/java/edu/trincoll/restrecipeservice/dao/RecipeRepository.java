package edu.trincoll.restrecipeservice.dao;

import edu.trincoll.restrecipeservice.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String>
{
}
