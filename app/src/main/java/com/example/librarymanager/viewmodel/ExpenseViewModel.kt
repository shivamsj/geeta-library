package com.example.librarymanager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.librarymanager.data.model.Expense
import com.example.librarymanager.data.repository.ExpenseRepository
import com.example.librarymanager.utils.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ExpenseUiState(
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = true,
    val message: String? = null,
    val error: String? = null
)

class ExpenseViewModel(
    private val repository: ExpenseRepository = ExpenseRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()
    init { refresh() }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        repository.load { result ->
            result.fold(
                onSuccess = { values -> _uiState.value = _uiState.value.copy(expenses = values, isLoading = false) },
                onFailure = { error -> _uiState.value = _uiState.value.copy(isLoading = false, error = error.message) }
            )
        }
    }

    fun save(expense: Expense): Boolean {
        val error = FormValidator.required(expense.title, "Expense title")
            ?: (if (expense.amount <= 0.0) "Enter a valid expense amount" else null)
            ?: FormValidator.required(expense.date, "Expense date")
        if (error != null) {
            _uiState.value = _uiState.value.copy(error = error, message = null)
            return false
        }
        repository.save(expense) { result ->
            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(message = "Expense saved", error = null)
                    refresh()
                },
                onFailure = { _uiState.value = _uiState.value.copy(error = it.message) }
            )
        }
        return true
    }

    fun delete(expense: Expense) {
        if (expense.id.isBlank()) return
        repository.delete(expense.id) { result ->
            result.fold(
                onSuccess = { refresh() },
                onFailure = { _uiState.value = _uiState.value.copy(error = it.message) }
            )
        }
    }
}
