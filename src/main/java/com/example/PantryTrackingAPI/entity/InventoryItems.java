package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;

@Entity
public class InventoryItems extends BaseEntity {
    @ColumnDefault("'NO BARCODE'")
    @Column(length = 13, nullable = false)
    private String barcode;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Brands brand;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Categories category;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Units unit;

    protected InventoryItems(){
        // Required by JPA
    }

    public InventoryItems(String barcode, String description, BigDecimal quantity,
                          Brands brand, Categories category, Units unit, String updatedBy){
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

    public Brands getBrand() { return brand; }

    public Categories getCategory() { return category; }

    public Units getUnit() { return unit; }
}
