package edu.trincoll.restrecipeservice.config;

import edu.trincoll.restrecipeservice.dao.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppConfig implements CommandLineRunner
{
    private final RecipeRepository repository;

    @Autowired
    public AppConfig(RecipeRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception
    {
        
    }
}
