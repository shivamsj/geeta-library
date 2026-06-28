package com.example.librarymanager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.librarymanager.data.repository.AuthRepository
import com.example.librarymanager.utils.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthUiState(
    val name: String = "",
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
    val message: String? = null
)

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState(isAuthenticated = repository.isSignedIn))
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        if (repository.isSignedIn) {
            repository.refreshSession { result ->
                result.exceptionOrNull()?.let { update { copy(error = it.message) } }
            }
        }
    }

    fun updateName(value: String) = update { copy(name = value, error = null) }
    fun updateMobile(value: String) = update { copy(mobile = value.filter(Char::isDigit).take(10), error = null) }
    fun updateEmail(value: String) = update { copy(email = value, error = null) }
    fun updatePassword(value: String) = update { copy(password = value, error = null) }
    fun updateConfirmPassword(value: String) = update { copy(confirmPassword = value, error = null) }

    fun login() {
        val state = _uiState.value
        val error = FormValidator.email(state.email) ?: FormValidator.password(state.password)
        if (error != null) return update { copy(error = error) }

        update { copy(isLoading = true, error = null, message = null) }
        repository.login(state.email, state.password) { result ->
            result.fold(
                onSuccess = { update { copy(isLoading = false, isAuthenticated = true) } },
                onFailure = { errorValue -> update { copy(isLoading = false, error = errorValue.message ?: "Login failed") } }
            )
        }
    }

    fun register() {
        val state = _uiState.value
        val error = FormValidator.required(state.name, "Full name")
            ?: FormValidator.mobile(state.mobile)
            ?: FormValidator.email(state.email)
            ?: FormValidator.password(state.password)
            ?: if (state.password != state.confirmPassword) "Passwords do not match" else null
        if (error != null) return update { copy(error = error) }

        update { copy(isLoading = true, error = null, message = null) }
        repository.register(state.name, state.mobile, state.email, state.password) { result ->
            result.fold(
                onSuccess = { update { copy(isLoading = false, isAuthenticated = true) } },
                onFailure = { errorValue -> update { copy(isLoading = false, error = errorValue.message ?: "Registration failed") } }
            )
        }
    }

    fun sendPasswordReset() {
        val emailError = FormValidator.email(_uiState.value.email)
        if (emailError != null) return update { copy(error = emailError) }
        update { copy(isLoading = true, error = null, message = null) }
        repository.sendPasswordReset(_uiState.value.email) { result ->
            result.fold(
                onSuccess = { update { copy(isLoading = false, message = "Request submitted. Check your email for further instructions.") } },
                onFailure = { errorValue -> update { copy(isLoading = false, error = errorValue.message ?: "Unable to send reset email") } }
            )
        }
    }

    fun consumeAuthentication() = update { copy(isAuthenticated = false) }
    fun clearFeedback() = update { copy(error = null, message = null) }

    fun signOut() {
        repository.signOut()
        _uiState.value = AuthUiState()
    }

    private inline fun update(transform: AuthUiState.() -> AuthUiState) {
        _uiState.value = _uiState.value.transform()
    }
}
