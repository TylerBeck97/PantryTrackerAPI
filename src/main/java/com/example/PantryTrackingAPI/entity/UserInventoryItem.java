package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_inventory_items")
public class UserInventoryItem extends BaseEntity{

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private LocalDate useByDate;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal remainingQuantity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private InventoryItem inventoryItem;

    public UserInventoryItem(){
        // Required by JPA
    }

    public UserInventoryItem(LocalDate purchaseDate, LocalDate useByDate, User user,
                             InventoryItem inventoryItem, String updatedBy){
        super(updatedBy);
        this.purchaseDate = purchaseDate;
        this.useByDate = useByDate;
        this.user = user;
        this.inventoryItem = inventoryItem;
        this.remainingQuantity = inventoryItem.getQuantity();

    }

    public LocalDate getPurchaseDate() { return purchaseDate; }

    public LocalDate getUseByDate() { return useByDate; }

    public BigDecimal getRemainingQuantity() { return remainingQuantity; }

    public User getUser() { return user; }

    public InventoryItem getInventoryItem() { return inventoryItem; }

    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public void setUseByDate(LocalDate useByDate) { this.useByDate = useByDate; }

    public void setRemainingQuantity(BigDecimal remainingQuantity) { this.remainingQuantity = remainingQuantity; }

    public void setUser(User user) { this.user = user; }

    public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }
}
