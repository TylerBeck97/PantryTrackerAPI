package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.Category;

public record CategoryDTO(Long id, String categoryName) {
    public static CategoryDTO fromEntity(Category category){
        return new CategoryDTO(
                category.getId(),
                category.getCategoryName()
        );
    }
}
