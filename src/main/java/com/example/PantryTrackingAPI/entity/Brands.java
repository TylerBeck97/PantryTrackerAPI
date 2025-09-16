package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;

@Entity
public class Brands extends BaseEntity {
    @Column(unique = true, nullable = false, length = 100)
    private String brandName;

    protected Brands(){
        // Required by JPA
    }

    public Brands(String brandName, String updatedBy){
        super(updatedBy);
        this.brandName = brandName;
    }

    public String getBrandName() { return brandName; }
}
