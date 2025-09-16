package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByUsernameOrEmail(String username, String email);
    Boolean existsByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
