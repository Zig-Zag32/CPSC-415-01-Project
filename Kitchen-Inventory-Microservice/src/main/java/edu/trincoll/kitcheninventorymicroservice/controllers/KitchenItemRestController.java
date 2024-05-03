package edu.trincoll.kitcheninventorymicroservice.controllers;

import edu.trincoll.kitcheninventorymicroservice.dao.KitchenItemRepository;
import edu.trincoll.kitcheninventorymicroservice.entities.KitchenItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public ResponseEntity<List<KitchenItem>> getAllKitchenItems() {
        List<KitchenItem> kitchenItems = repository.findAll();
        if (kitchenItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(kitchenItems);
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

    /*@PostMapping("/kitchenItems")
    public ResponseEntity<KitchenItem>[] createMultipleNewItems(@RequestBody KitchenItem[] items)
    {
        ResponseEntity<KitchenItem>[] addedItems = new ResponseEntity[items.length];
        int addedItemsIndex = 0;

        for (KitchenItem item : items)
        {
            if (repository.findById(item.getName()).equals(Optional.empty()))
            {
                item.setAmount((float) 0);
                repository.save(item);
                URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/{id}")
                        .buildAndExpand(item.getName())
                        .toUri();
                addedItems[addedItemsIndex] = ResponseEntity.created(location).body(item);
            }
        }
        return addedItems;
    }*/
}
