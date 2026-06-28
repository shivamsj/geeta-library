package com.example.librarymanager.data.repository

import com.example.librarymanager.data.network.ApiClient
import com.example.librarymanager.data.network.AuthResponse
import com.example.librarymanager.data.network.ForgotPasswordRequest
import com.example.librarymanager.data.network.LoginRequest
import com.example.librarymanager.data.network.MessageResponse
import com.example.librarymanager.data.network.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AuthRepository {
    val isSignedIn: Boolean get() = ApiClient.isSignedIn

    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        ApiClient.service.login(LoginRequest(email.trim(), password)).enqueue(authCallback(onResult))
    }

    fun register(name: String, mobile: String, email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        ApiClient.service.register(RegisterRequest(name.trim(), mobile.trim(), email.trim(), password))
            .enqueue(authCallback(onResult))
    }

    fun sendPasswordReset(email: String, onResult: (Result<Unit>) -> Unit) {
        ApiClient.service.forgotPassword(ForgotPasswordRequest(email.trim())).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) onResult(Result.success(Unit))
                else onResult(Result.failure(apiError(response.code())))
            }

            override fun onFailure(call: Call<MessageResponse>, throwable: Throwable) = onResult(Result.failure(connectionError(throwable)))
        })
    }

    fun signOut() = ApiClient.clearToken()

    fun refreshSession(onResult: (Result<Unit>) -> Unit) {
        ApiClient.service.currentUser().enqueue(authCallback(onResult))
    }

    private fun authCallback(onResult: (Result<Unit>) -> Unit) = object : Callback<AuthResponse> {
        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiClient.saveSession(body)
                onResult(Result.success(Unit))
            } else onResult(Result.failure(apiError(response.code())))
        }

        override fun onFailure(call: Call<AuthResponse>, throwable: Throwable) = onResult(Result.failure(connectionError(throwable)))
    }

    private fun apiError(code: Int) = IllegalStateException("Server request failed (HTTP $code)")

    private fun connectionError(throwable: Throwable): Throwable =
        if (throwable is IOException) {
            IllegalStateException("Server is unavailable. Start the backend and keep the phone and computer on the same Wi-Fi.")
        } else throwable
}
