package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;
import java.util.Set;

@Entity
public class Users extends BaseEntity{

    @Column(length = 64, nullable = false, unique = true)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @ColumnDefault("NULL")
    @Column(length = 20)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Roles> roles;

    protected Users() {
        // Required by JPA
    }

    public Users(String username, String password, String email, String phoneNumber, Set<Roles> roles, String updatedBy){
        super(updatedBy);
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public String getPhoneNumber() { return phoneNumber; }

    public Set<Roles> getRoles() { return roles; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Users other = (Users) obj;
        Long id = getId();
        return id.equals(other.getId()) && username.equals(other.username)
                && email.equals(other.email) && phoneNumber.equals(other.phoneNumber) && password.equals(other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), username, email, phoneNumber, password);
    }
}

