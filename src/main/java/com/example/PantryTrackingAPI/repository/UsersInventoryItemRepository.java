package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.User;
import com.example.PantryTrackingAPI.entity.UserInventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersInventoryItemRepository extends CrudRepository<UserInventoryItem, Long> {
    List<UserInventoryItem> findByUserUsername(String username);
    Optional<UserInventoryItem> findByInventoryItemBarcodeAndUser(String barcode, User user);
}
