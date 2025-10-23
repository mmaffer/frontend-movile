package com.example.matchpet.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val city: String?,
    val preferences: PreferenceForm?
)
