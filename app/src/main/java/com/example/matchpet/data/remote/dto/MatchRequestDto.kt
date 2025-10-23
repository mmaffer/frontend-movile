package com.example.matchpet.data.remote.dto

data class MatchRequestDto(
    val adopterId: String?,
    val preferences: PreferenceFormDto,
    val limit: Int = 10
)
