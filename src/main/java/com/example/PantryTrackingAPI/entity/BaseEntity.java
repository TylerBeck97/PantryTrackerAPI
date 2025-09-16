package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;

import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @Column(length = 64)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    protected BaseEntity(){
        // Required by JPA
    }

    public BaseEntity(String updatedBy){
        this.updatedBy = updatedBy;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public String getUpdatedBy() { return updatedBy; }

    public void setId(Long id) { this.id = id; }

    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}
