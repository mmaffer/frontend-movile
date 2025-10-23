package com.example.matchpet.domain.model

import java.io.Serializable

data class PreferenceForm(
    val housingType: String = "",
    val hasOutdoorSpace: Boolean = false,
    val activityLevel: String = "",
    val preferredSpecies: String = "",
    val preferredSize: String = "",
    val experienceLevel: String = "",
    val householdNotes: String? = null
) : Serializable
