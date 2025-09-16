package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Roles extends BaseEntity{
    @Column(length = 45, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users;

    protected Roles() {
        // Required by JPA
    }

    public Roles(String name, String updatedBy) {
        super(updatedBy);
        this.name = name;
    }

    public String getName() { return name; }
}
