package edu.trincoll.kitcheninventorymicroservice.dao;

import edu.trincoll.kitcheninventorymicroservice.entities.KitchenItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenItemRepository extends JpaRepository<KitchenItem, String>
{
}
