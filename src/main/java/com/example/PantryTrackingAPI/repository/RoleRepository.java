package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
