package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.Category;

public record CategoriesDTO(Long id, String categoryName) {
    public static CategoriesDTO fromEntity(Category category){
        return new CategoriesDTO(
                category.getId(),
                category.getCategoryName()
        );
    }
}
