package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.User;

public record UserDTO(String username, String email, String phoneNumber) {
    public static UserDTO fromEntity(User user){
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
