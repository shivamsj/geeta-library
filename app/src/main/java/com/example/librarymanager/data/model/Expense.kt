package com.example.librarymanager.data.model

data class Expense(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val date: String = "",
    val description: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
