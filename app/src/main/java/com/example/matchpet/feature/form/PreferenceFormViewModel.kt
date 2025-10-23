package com.example.matchpet.feature.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchpet.domain.model.PreferenceForm
import com.example.matchpet.domain.repository.AdoptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PreferenceFormUiState(
    val form: PreferenceForm = PreferenceForm(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)

class PreferenceFormViewModel(
    private val adoptionRepository: AdoptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PreferenceFormUiState())
    val uiState: StateFlow<PreferenceFormUiState> = _uiState

    fun updateForm(transform: PreferenceForm.() -> PreferenceForm) {
        _uiState.update { state ->
            state.copy(form = state.form.transform(), error = null)
        }
    }

    fun submitForm() {
        val form = _uiState.value.form
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = adoptionRepository.submitPreferences(form)
            when {
                result.error != null -> _uiState.update {
                    it.copy(isLoading = false, error = result.error, saved = false)
                }

                else -> _uiState.update {
                    it.copy(isLoading = false, saved = true)
                }
            }
        }
    }

    fun consumeSuccess() {
        _uiState.update { it.copy(saved = false) }
    }
}
