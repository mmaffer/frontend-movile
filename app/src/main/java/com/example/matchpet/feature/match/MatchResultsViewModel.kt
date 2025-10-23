package com.example.matchpet.feature.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchpet.domain.model.MatchResult
import com.example.matchpet.domain.model.PreferenceForm
import com.example.matchpet.domain.repository.AdoptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatchResultsUiState(
    val result: MatchResult? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class MatchResultsViewModel(
    private val adoptionRepository: AdoptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchResultsUiState())
    val uiState: StateFlow<MatchResultsUiState> = _uiState

    fun loadMatches(form: PreferenceForm) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, result = null) }
            val result = adoptionRepository.fetchMatches(form)
            when {
                result.error != null -> _uiState.update {
                    it.copy(isLoading = false, error = result.error, result = null)
                }

                result.data != null -> _uiState.update {
                    it.copy(isLoading = false, result = result.data)
                }

                else -> _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
