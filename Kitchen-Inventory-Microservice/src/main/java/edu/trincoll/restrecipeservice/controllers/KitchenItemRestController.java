package edu.trincoll.restrecipeservice.controllers;

import edu.trincoll.restrecipeservice.dao.KitchenItemRepository;
import edu.trincoll.restrecipeservice.entities.KitchenItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class KitchenItemRestController
{
    private final KitchenItemRepository repository;

    @Autowired
    public KitchenItemRestController(KitchenItemRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/ingredients")
    public List<KitchenItem> getAllIngredients()
    {
        return repository.findAll();
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<KitchenItem> getIngredientsById(@PathVariable Long id)
    {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<KitchenItem> deleteIngredientById(@PathVariable Long id)
    {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ingredients")
    public ResponseEntity<KitchenItem> createIngredient(@RequestBody KitchenItem kitchenItem)
    {
        repository.save(kitchenItem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(kitchenItem.getId())
                .toUri();
        return ResponseEntity.created(location).body(kitchenItem);
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<KitchenItem> updateIngredient(@PathVariable Long id, @RequestBody KitchenItem kitchenItemUpdate) {

        KitchenItem kitchenItem = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingredient not found with id " + id));

        kitchenItem.setName(kitchenItemUpdate.getName());
        kitchenItem.setAmount(kitchenItemUpdate.getAmount());
        kitchenItem.setUnit(kitchenItemUpdate.getUnit());

        KitchenItem updatedKitchenItem = repository.save(kitchenItem);

        return ResponseEntity.ok(updatedKitchenItem);
    }
}
