package com.geetalibrary.backend.auth.dto;

public record AuthResponse(
    String token,
    String tokenType,
    String userId,
    String name,
    String email,
    String role
) {}
