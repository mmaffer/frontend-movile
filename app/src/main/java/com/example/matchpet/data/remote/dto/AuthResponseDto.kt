package com.example.matchpet.data.remote.dto

data class AuthResponseDto(
    val accessToken: String,
    val refreshToken: String?,
    val user: UserProfileDto
)
