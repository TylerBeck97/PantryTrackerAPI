package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Column(unique = true, nullable = false, length = 100)
    private String brandName;

    protected Brand(){
        // Required by JPA
    }

    public Brand(String brandName, String updatedBy){
        super(updatedBy);
        this.brandName = brandName;
    }

    public String getBrandName() { return brandName; }
}
