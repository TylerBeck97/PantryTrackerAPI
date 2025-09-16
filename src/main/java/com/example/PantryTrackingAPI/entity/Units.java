package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Units extends BaseEntity{
    @Column(length = 50)
    private String unitName;

    protected Units(){
        // Required by JPA
    }

    public Units(String unitName, String updatedBy){
        super(updatedBy);
        this.unitName = unitName;
    }

    public String getUnitName() { return unitName; }
}
