package com.example.matchpet.data.remote.dto

data class PreferenceFormDto(
    val housingType: String,
    val hasOutdoorSpace: Boolean,
    val activityLevel: String,
    val preferredSpecies: String,
    val preferredSize: String,
    val experienceLevel: String,
    val householdNotes: String?
)
