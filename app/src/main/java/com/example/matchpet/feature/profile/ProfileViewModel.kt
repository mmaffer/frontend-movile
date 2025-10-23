package com.example.matchpet.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchpet.core.datastore.SessionPreferences
import com.example.matchpet.domain.model.User
import com.example.matchpet.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedOut: Boolean = false
)

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val sessionPreferences: SessionPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        observeSession()
        refreshProfile()
    }

    private fun observeSession() {
        viewModelScope.launch {
            sessionPreferences.accessToken.collectLatest { token ->
                if (token.isNullOrBlank()) {
                    _uiState.update { it.copy(isLoggedOut = true, user = null) }
                }
            }
        }
    }

    fun refreshProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.getProfile()
            when {
                result.error != null -> _uiState.update {
                    it.copy(isLoading = false, error = result.error)
                }

                result.data != null -> _uiState.update {
                    it.copy(isLoading = false, user = result.data)
                }

                else -> _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isLoggedOut = true, user = null) }
        }
    }

    fun consumeLogout() {
        _uiState.update { it.copy(isLoggedOut = false) }
    }
}
