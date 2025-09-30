package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.InventoryItem;

import java.math.BigDecimal;

public record InventoryItemDTO(Long id,
                               String description,
                               String barcode,
                               BigDecimal quantity,
                               String brand,
                               String category,
                               String unit) {
    public static InventoryItemDTO fromEntity(InventoryItem item){
        return new InventoryItemDTO(
                item.getId(),
                item.getDescription(),
                item.getBarcode(),
                item.getQuantity(),
                item.getBrand().getBrandName(),
                item.getCategory().getCategoryName(),
                item.getUnit().getUnitName()
        );
    }
}

