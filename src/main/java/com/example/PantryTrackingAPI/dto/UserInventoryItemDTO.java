package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.UserInventoryItem;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserInventoryItemDTO(Long id, LocalDate purchaseDate, LocalDate useByDate, BigDecimal remainingQuantity,
                                   InventoryItemDTO inventoryItems) {
    public static UserInventoryItemDTO fromEntity(UserInventoryItem item){
        return new UserInventoryItemDTO(
                item.getId(),
                item.getPurchaseDate(),
                item.getUseByDate(),
                item.getRemainingQuantity(),
                InventoryItemDTO.fromEntity(item.getInventoryItem())
        );
    }
}
