package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Categories extends BaseEntity {
    @Column(unique = true, nullable = false, length = 50)
    private String categoryName;

    protected Categories() {
        // Required by JPA
    }

    public Categories(String categoryName, String updatedBy){
        super(updatedBy);
        this.categoryName = categoryName;
    }

    private String getCategoryName() { return categoryName; }
}
