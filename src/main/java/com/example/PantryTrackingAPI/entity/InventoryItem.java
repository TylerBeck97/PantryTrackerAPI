package com.example.PantryTrackingAPI.entity;

import com.example.PantryTrackingAPI.enums.Units;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class InventoryItem {
    @Id @GeneratedValue
    private long id;
    private String description;
    private List<String> barcodes;
    private float quantity;
    private Units units;
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public InventoryItem() {
    }

    public InventoryItem(String description, List<String> barcodes,
                         float quantity, Units units, LocalDate expirationDate, User user) {
        this.description = description;
        this.barcodes = barcodes;
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

    public List<String> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<String> barcodes) {
        this.barcodes = barcodes;
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
