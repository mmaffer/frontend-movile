package com.example.matchpet.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.matchpet.core.datastore.SessionPreferences
import com.example.matchpet.domain.repository.AdoptionRepository
import com.example.matchpet.domain.repository.AuthRepository
import com.example.matchpet.feature.auth.login.LoginViewModel
import com.example.matchpet.feature.auth.register.RegisterViewModel
import com.example.matchpet.feature.form.PreferenceFormViewModel
import com.example.matchpet.feature.match.MatchResultsViewModel
import com.example.matchpet.feature.profile.ProfileViewModel

class MatchPetViewModelFactory(
    private val authRepository: AuthRepository,
    private val adoptionRepository: AdoptionRepository,
    private val sessionPreferences: SessionPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(LoginViewModel::class.java) ->
            LoginViewModel(authRepository) as T

        modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
            RegisterViewModel(authRepository) as T

        modelClass.isAssignableFrom(PreferenceFormViewModel::class.java) ->
            PreferenceFormViewModel(adoptionRepository) as T

        modelClass.isAssignableFrom(MatchResultsViewModel::class.java) ->
            MatchResultsViewModel(adoptionRepository) as T

        modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
            ProfileViewModel(authRepository, sessionPreferences) as T

        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
