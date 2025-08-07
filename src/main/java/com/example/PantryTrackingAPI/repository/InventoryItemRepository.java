package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {
    List<InventoryItem> findByOwnerUsername(String username);
}
