package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(unique = true, nullable = false, length = 50)
    private String categoryName;

    protected Category() {
        // Required by JPA
    }

    public Category(String categoryName, String updatedBy){
        super(updatedBy);
        this.categoryName = categoryName;
    }

    public String getCategoryName() { return categoryName; }
}
