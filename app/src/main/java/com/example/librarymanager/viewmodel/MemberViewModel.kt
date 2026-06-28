package com.example.librarymanager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.librarymanager.data.model.Member
import com.example.librarymanager.data.repository.MemberRepository
import com.example.librarymanager.utils.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MemberUiState(
    val members: List<Member> = emptyList(),
    val isLoading: Boolean = true,
    val message: String? = null,
    val error: String? = null
)

class MemberViewModel(
    private val repository: MemberRepository = MemberRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemberUiState())
    val uiState: StateFlow<MemberUiState> = _uiState.asStateFlow()
    init { refresh() }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        repository.load { result ->
            result.fold(
                onSuccess = { members -> _uiState.value = _uiState.value.copy(members = members, isLoading = false) },
                onFailure = { error -> _uiState.value = _uiState.value.copy(isLoading = false, error = error.message) }
            )
        }
    }

    fun save(member: Member): Boolean {
        val error = FormValidator.required(member.name, "Member name") ?: FormValidator.mobile(member.mobile)
        if (error != null) {
            _uiState.value = _uiState.value.copy(error = error, message = null)
            return false
        }
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        repository.save(member) { result ->
            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(message = "Member saved")
                    refresh()
                },
                onFailure = { _uiState.value = _uiState.value.copy(isLoading = false, error = it.message) }
            )
        }
        return true
    }

    fun delete(member: Member) {
        if (member.id.isBlank()) return
        repository.delete(member.id) { result ->
            result.fold(
                onSuccess = { refresh() },
                onFailure = { _uiState.value = _uiState.value.copy(error = it.message) }
            )
        }
    }
}
