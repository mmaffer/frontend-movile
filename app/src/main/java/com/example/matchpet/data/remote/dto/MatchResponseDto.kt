package com.example.matchpet.data.remote.dto

data class MatchResponseDto(
    val matches: List<PetDto>,
    val generatedAt: String
)
