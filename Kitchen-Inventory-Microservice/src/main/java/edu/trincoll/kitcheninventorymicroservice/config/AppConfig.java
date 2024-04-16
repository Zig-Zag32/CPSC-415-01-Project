package edu.trincoll.kitcheninventorymicroservice.config;

import edu.trincoll.kitcheninventorymicroservice.dao.KitchenItemRepository;
import edu.trincoll.kitcheninventorymicroservice.entities.KitchenItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppConfig implements CommandLineRunner
{
    private final KitchenItemRepository repository;

    @Autowired
    public AppConfig(KitchenItemRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception
    {
        repository.saveAll(
            List.of(
                    new KitchenItem(Float.valueOf(3), "Bags", "Spring Beans"),
                    new KitchenItem(Float.valueOf(2), "Cups", "Java"),
                    new KitchenItem(Float.valueOf(12), "Bantam Eggs")
            )
        ).forEach(System.out::println);
    }
}
