package com.example.matchpet.data.remote.dto

data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val phone: String?,
    val city: String?
)
