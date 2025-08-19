package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.InventoryItem;
import com.example.PantryTrackingAPI.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {
    List<InventoryItem> findByOwnerUsername(String username);
    Optional<InventoryItem> findByBarcodeAndOwner(String barcode, User owner);
}
