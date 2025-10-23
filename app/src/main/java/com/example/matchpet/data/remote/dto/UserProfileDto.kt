package com.example.matchpet.data.remote.dto

data class UserProfileDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val city: String?,
    val preferences: PreferenceFormDto?
)
