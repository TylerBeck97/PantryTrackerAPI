package com.example.PantryTrackingAPI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(length = 64, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
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
    private Set<Role> roles;

    protected User() {
        // Required by JPA
    }

    public User(String username, String password, String email, String phoneNumber, Set<Role> roles, String updatedBy){
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

    public Set<Role> getRoles() { return roles; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        Long id = getId();
        return id.equals(other.getId()) && username.equals(other.username)
                && email.equals(other.email) && phoneNumber.equals(other.phoneNumber) && password.equals(other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), username, email, phoneNumber, password);
    }
}

