package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.Brand;

public record BrandDTO(Long id, String brandName) {
    public static BrandDTO fromEntity(Brand brand){
        return new BrandDTO(
                brand.getId(),
                brand.getBrandName()
        );
    }
}
