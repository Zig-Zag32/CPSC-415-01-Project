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

    @GetMapping("/kitchenItems")
    public List<KitchenItem> getAllKitchenItems()
    {
        return repository.findAll();
    }

    @GetMapping("/kitchenItems/{id}")
    public ResponseEntity<KitchenItem> getKitchenItemsById(@PathVariable String id)
    {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/kitchenItems/{id}")
    public ResponseEntity<KitchenItem> deleteKitchenItemById(@PathVariable String id)
    {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/kitchenItems")
    public ResponseEntity<KitchenItem> createKitchenItem(@RequestBody KitchenItem kitchenItem)
    {
        repository.save(kitchenItem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(kitchenItem.getName())
                .toUri();
        return ResponseEntity.created(location).body(kitchenItem);
    }

    @PutMapping("/kitchenItems/{id}")
    public ResponseEntity<KitchenItem> updateKitchenItem(@PathVariable String id, @RequestBody KitchenItem kitchenItemUpdate) {

        KitchenItem kitchenItem = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Kitchen Item not found with id " + id));
        deleteKitchenItemById(id);

        KitchenItem updatedKitchenItem = repository.save(kitchenItem);

        return ResponseEntity.ok(updatedKitchenItem);
    }
}
