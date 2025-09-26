package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.User;

public record UsersDTO(String username, String email, String phoneNumber) {
    public static UsersDTO fromEntity(User user){
        return new UsersDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
