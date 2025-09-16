package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.InventoryItems;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryItemRepository extends CrudRepository<InventoryItems, Long> {
    Optional<InventoryItems> findByBarcode(String barcode);
}
