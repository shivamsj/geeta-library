package com.example.librarymanager.data.repository

import com.example.librarymanager.data.model.Expense
import com.example.librarymanager.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseRepository {
    fun load(onResult: (Result<List<Expense>>) -> Unit) {
        ApiClient.service.getExpenses().enqueue(object : Callback<List<Expense>> {
            override fun onResponse(call: Call<List<Expense>>, response: Response<List<Expense>>) {
                val body = response.body()
                if (response.isSuccessful && body != null) onResult(Result.success(body))
                else onResult(Result.failure(apiError(response.code())))
            }

            override fun onFailure(call: Call<List<Expense>>, throwable: Throwable) = onResult(Result.failure(throwable))
        })
    }

    fun save(expense: Expense, onResult: (Result<Unit>) -> Unit) {
        val call = if (expense.id.isBlank()) ApiClient.service.createExpense(expense)
        else ApiClient.service.updateExpense(expense.id, expense)
        call.enqueue(object : Callback<Expense> {
            override fun onResponse(call: Call<Expense>, response: Response<Expense>) {
                if (response.isSuccessful) onResult(Result.success(Unit))
                else onResult(Result.failure(apiError(response.code())))
            }

            override fun onFailure(call: Call<Expense>, throwable: Throwable) = onResult(Result.failure(throwable))
        })
    }

    fun delete(id: String, onResult: (Result<Unit>) -> Unit) {
        ApiClient.service.deleteExpense(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) onResult(Result.success(Unit))
                else onResult(Result.failure(apiError(response.code())))
            }

            override fun onFailure(call: Call<Void>, throwable: Throwable) = onResult(Result.failure(throwable))
        })
    }

    private fun apiError(code: Int) = IllegalStateException("Expense request failed (HTTP $code)")
}
