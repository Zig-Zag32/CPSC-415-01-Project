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
                    new KitchenItem(1F, "Jar", "Salsa"),
                    new KitchenItem(1.5F, "Pounds", "Ground Beef"),
                    new KitchenItem(4F, "Tortilla"),
                    new KitchenItem(1F, "Bag", "Shredded Cheese")
            )
        ).forEach(System.out::println);
    }
}
