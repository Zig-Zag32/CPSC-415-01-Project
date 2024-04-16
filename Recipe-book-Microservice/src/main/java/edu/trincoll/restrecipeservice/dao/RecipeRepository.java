package edu.trincoll.restrecipeservice.dao;

import edu.trincoll.restrecipeservice.entities.KitchenItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenItemRepository extends JpaRepository<KitchenItem, String>
{
}
