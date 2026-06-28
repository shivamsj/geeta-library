package com.example.librarymanager.data.model

data class Member(
    val id: String = "",
    val name: String = "",
    val mobile: String = "",
    val address: String = "",
    val seatNumber: String = "",
    val planName: String = "",
    val joiningDate: String = "",
    val expiryDate: String = "",
    val dueAmount: Double = 0.0,
    val status: String = "ACTIVE",
    val updatedAt: Long = System.currentTimeMillis()
)
