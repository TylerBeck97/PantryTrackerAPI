package com.example.PantryTrackingAPI.entity;

import com.example.PantryTrackingAPI.repository.UsersInventoryItemRepository;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class UsersInventoryItem extends BaseEntity{

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private LocalDate useByDate;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal remainingQuantity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Users user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private InventoryItems inventoryItem;

    protected UsersInventoryItem(){
        // Required by JPA
    }

    public UsersInventoryItem(LocalDate purchaseDate, LocalDate useByDate, Users user,
                              InventoryItems inventoryItem, String updatedBy){
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

    public Users getUser() { return user; }

    public InventoryItems getInventoryItem() { return inventoryItem; }
}
