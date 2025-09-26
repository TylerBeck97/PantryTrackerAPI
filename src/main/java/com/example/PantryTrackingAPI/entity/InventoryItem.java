package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory_items")
public class InventoryItem extends BaseEntity {
    @ColumnDefault("'NO BARCODE'")
    @Column(length = 13, nullable = false)
    private String barcode;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Brand brand;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Unit unit;

    public InventoryItem(){
        // Required by JPA
    }

    public InventoryItem(String barcode, String description, BigDecimal quantity,
                         Brand brand, Category category, Unit unit, String updatedBy){
        super(updatedBy);
        this.barcode = barcode;
        this.description = description;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
        this.unit = unit;
    }

    public String getBarcode() { return barcode; }

    public String getDescription() { return description; }

    public BigDecimal getQuantity() { return quantity; }

    public Brand getBrand() { return brand; }

    public Category getCategory() { return category; }

    public Unit getUnit() { return unit; }

    public void setBarcode(String barcode) { this.barcode = barcode; }

    public void setDescription(String description) { this.description = description; }

    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public void setBrand(Brand brand) { this.brand = brand; }

    public void setCategory(Category category) { this.category = category; }

    public void setUnit(Unit unit) { this.unit = unit; }
}
