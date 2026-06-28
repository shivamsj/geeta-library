package com.example.librarymanager.data.network

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(
    val name: String,
    val mobile: String,
    val email: String,
    val password: String
)

data class ForgotPasswordRequest(val email: String)

data class AuthResponse(
    val token: String,
    val tokenType: String = "Bearer",
    val userId: String,
    val name: String,
    val email: String,
    val role: String
)

data class SessionUser(
    val userId: String,
    val name: String,
    val email: String,
    val role: String
)

data class MessageResponse(val message: String)
