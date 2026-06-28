package com.example.librarymanager.utils

import android.util.Patterns

object FormValidator {
    fun required(value: String, field: String): String? =
        if (value.trim().isEmpty()) "$field is required" else null

    fun email(value: String): String? = when {
        value.isBlank() -> "Email is required"
        !Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches() -> "Enter a valid email address"
        else -> null
    }

    fun mobile(value: String): String? = when {
        value.isBlank() -> "Mobile number is required"
        !value.matches(Regex("^[6-9][0-9]{9}$")) -> "Enter a valid 10-digit mobile number"
        else -> null
    }

    fun password(value: String): String? = when {
        value.isBlank() -> "Password is required"
        value.length < 6 -> "Password must contain at least 6 characters"
        else -> null
    }
}
