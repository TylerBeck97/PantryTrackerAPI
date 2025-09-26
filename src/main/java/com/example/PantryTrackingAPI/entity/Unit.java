package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "units")
public class Unit extends BaseEntity{
    @Column(length = 50)
    private String unitName;

    protected Unit(){
        // Required by JPA
    }

    public Unit(String unitName, String updatedBy){
        super(updatedBy);
        this.unitName = unitName;
    }

    public String getUnitName() { return unitName; }
}
