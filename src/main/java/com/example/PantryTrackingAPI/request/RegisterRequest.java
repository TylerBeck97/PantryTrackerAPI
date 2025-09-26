package com.example.PantryTrackingAPI.request;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull String username,
        @NotNull String password,
        @NotNull String email,
        @NotNull String phoneNumber) {

}
