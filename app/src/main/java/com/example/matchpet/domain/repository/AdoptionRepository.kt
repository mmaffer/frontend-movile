package com.example.matchpet.domain.repository

import com.example.matchpet.core.util.ResultState
import com.example.matchpet.domain.model.MatchResult
import com.example.matchpet.domain.model.PreferenceForm

interface AdoptionRepository {
    suspend fun submitPreferences(form: PreferenceForm): ResultState<Unit>
    suspend fun fetchMatches(form: PreferenceForm): ResultState<MatchResult>
}
