package com.example.librarymanager.data.network

import com.example.librarymanager.data.model.Expense
import com.example.librarymanager.data.model.Member
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<MessageResponse>

    @GET("auth/me")
    fun currentUser(): Call<AuthResponse>

    @GET("members")
    fun getMembers(): Call<List<Member>>

    @POST("members")
    fun createMember(@Body member: Member): Call<Member>

    @PUT("members/{id}")
    fun updateMember(@Path("id") id: String, @Body member: Member): Call<Member>

    @DELETE("members/{id}")
    fun deleteMember(@Path("id") id: String): Call<Void>

    @GET("expenses")
    fun getExpenses(): Call<List<Expense>>

    @POST("expenses")
    fun createExpense(@Body expense: Expense): Call<Expense>

    @PUT("expenses/{id}")
    fun updateExpense(@Path("id") id: String, @Body expense: Expense): Call<Expense>

    @DELETE("expenses/{id}")
    fun deleteExpense(@Path("id") id: String): Call<Void>
}
