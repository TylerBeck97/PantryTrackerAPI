package com.example.PantryTrackingAPI.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserInventoryItemRequest(
        Long id,
        LocalDate purchaseDate,
        LocalDate useByDate,
        BigDecimal remainingQuantity,
        String inventoryItemBarcode) {
}
