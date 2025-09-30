package com.example.PantryTrackingAPI.request;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        String username,
        String password,
        String email,
        String phoneNumber) {
}
