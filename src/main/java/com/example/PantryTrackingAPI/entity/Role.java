package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Column(length = 45, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    protected Role() {
        // Required by JPA
    }

    public Role(String name, String updatedBy) {
        super(updatedBy);
        this.name = name;
    }

    public String getName() { return name; }
}
