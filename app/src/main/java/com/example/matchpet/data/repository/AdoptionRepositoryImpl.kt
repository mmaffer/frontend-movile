package com.example.matchpet.data.repository

import com.example.matchpet.core.util.ResultState
import com.example.matchpet.data.mapper.toDomain
import com.example.matchpet.data.mapper.toDto
import com.example.matchpet.data.remote.api.MatchPetApi
import com.example.matchpet.data.remote.dto.MatchRequestDto
import com.example.matchpet.domain.model.MatchResult
import com.example.matchpet.domain.model.PreferenceForm
import com.example.matchpet.domain.repository.AdoptionRepository

class AdoptionRepositoryImpl(
    private val api: MatchPetApi
) : AdoptionRepository {
    override suspend fun submitPreferences(form: PreferenceForm): ResultState<Unit> = try {
        api.updatePreferences(form.toDto())
        ResultState.success(Unit)
    } catch (exception: Exception) {
        ResultState.error(exception.message ?: "No se pudo guardar el formulario")
    }

    override suspend fun fetchMatches(form: PreferenceForm): ResultState<MatchResult> = try {
        val response = api.getMatches(MatchRequestDto(adopterId = null, preferences = form.toDto()))
        ResultState.success(
            MatchResult(
                pets = response.matches.map { it.toDomain() },
                generatedAt = response.generatedAt
            )
        )
    } catch (exception: Exception) {
        ResultState.error(exception.message ?: "No se pudieron obtener coincidencias")
    }
}
