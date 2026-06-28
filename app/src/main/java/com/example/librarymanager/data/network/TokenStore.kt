package com.example.librarymanager.data.network

import android.content.Context

class TokenStore(context: Context) {
    private val preferences = context.getSharedPreferences("geeta_auth", Context.MODE_PRIVATE)

    val token: String? get() = preferences.getString("jwt", null)
    val user: SessionUser? get() {
        val userId = preferences.getString("user_id", null) ?: return null
        return SessionUser(
            userId = userId,
            name = preferences.getString("name", "Geeta User").orEmpty(),
            email = preferences.getString("email", "").orEmpty(),
            role = preferences.getString("role", "ADMIN").orEmpty()
        )
    }

    fun save(response: AuthResponse) {
        preferences.edit()
            .putString("jwt", response.token)
            .putString("user_id", response.userId)
            .putString("name", response.name)
            .putString("email", response.email)
            .putString("role", response.role)
            .apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
