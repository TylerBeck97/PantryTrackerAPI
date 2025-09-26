package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.UserInventoryItem;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UsersInventoryItemDTO(Long id, LocalDate purchaseDate, LocalDate useByDate, BigDecimal remainingQuantity,
                                    UsersDTO user, InventoryItemsDTO inventoryItems) {
    public static UsersInventoryItemDTO fromEntity(UserInventoryItem item){
        return new UsersInventoryItemDTO(
                item.getId(),
                item.getPurchaseDate(),
                item.getUseByDate(),
                item.getRemainingQuantity(),
                UsersDTO.fromEntity(item.getUser()),
                InventoryItemsDTO.fromEntity(item.getInventoryItem())
        );
    }
}
