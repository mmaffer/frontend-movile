package com.example.matchpet.feature.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchpet.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val city: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val registered: Boolean = false
)

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value, error = null) }
    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value, error = null) }
    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value, error = null) }
    fun onPhoneChange(value: String) = _uiState.update { it.copy(phone = value, error = null) }
    fun onCityChange(value: String) = _uiState.update { it.copy(city = value, error = null) }

    fun register() {
        val current = _uiState.value
        if (current.name.isBlank() || current.email.isBlank() || current.password.isBlank()) {
            _uiState.update { it.copy(error = "Completa los campos obligatorios") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.register(
                name = current.name,
                email = current.email,
                password = current.password,
                phone = current.phone.ifBlank { null },
                city = current.city.ifBlank { null }
            )

            when {
                result.error != null -> _uiState.update {
                    it.copy(isLoading = false, error = result.error, registered = false)
                }

                result.data != null -> _uiState.update {
                    it.copy(isLoading = false, registered = true)
                }

                else -> _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun consumeSuccess() {
        _uiState.update { it.copy(registered = false) }
    }
}
