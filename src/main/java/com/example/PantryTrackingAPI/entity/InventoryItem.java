package com.example.PantryTrackingAPI.entity;

import com.example.PantryTrackingAPI.enums.Units;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class InventoryItem {
    @Id @GeneratedValue
    private long id;
    private String description;
    private String barcode;
    private float quantity;
    private Units units;
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public InventoryItem() {
    }

    public InventoryItem(String description, String barcode,
                         float quantity, Units units, LocalDate expirationDate, User user) {
        this.description = description;
        this.barcode = barcode;
        this.quantity = quantity;
        this.units = units;
        this.expirationDate = expirationDate;
        this.owner = user;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarcodes() {
        return barcode;
    }

    public void setBarcodes(String barcode) {
        this.barcode = barcode;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
