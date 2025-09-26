package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByBarcode(String barcode);
}
