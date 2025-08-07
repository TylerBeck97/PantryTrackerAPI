package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.InventoryItem;
import com.example.PantryTrackingAPI.enums.Units;

import java.time.LocalDate;
import java.util.List;

public record InventoryItemDTO(Long id, String description, List<String> barcodes, Float quantity, Units units, LocalDate expirationDate) {
    public static InventoryItemDTO fromEntity(InventoryItem item){
        return new InventoryItemDTO(
                item.getId(),
                item.getDescription(),
                item.getBarcodes(),
                item.getQuantity(),
                item.getUnits(),
                item.getExpirationDate()
        );
    }
}
