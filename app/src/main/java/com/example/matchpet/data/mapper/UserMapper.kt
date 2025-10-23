package com.example.matchpet.data.mapper

import com.example.matchpet.data.remote.dto.PreferenceFormDto
import com.example.matchpet.data.remote.dto.PetDto
import com.example.matchpet.data.remote.dto.UserProfileDto
import com.example.matchpet.domain.model.PreferenceForm
import com.example.matchpet.domain.model.Pet
import com.example.matchpet.domain.model.User

fun UserProfileDto.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    city = city,
    preferences = preferences?.toDomain()
)

fun PreferenceFormDto.toDomain(): PreferenceForm = PreferenceForm(
    housingType = housingType,
    hasOutdoorSpace = hasOutdoorSpace,
    activityLevel = activityLevel,
    preferredSpecies = preferredSpecies,
    preferredSize = preferredSize,
    experienceLevel = experienceLevel,
    householdNotes = householdNotes
)

fun PreferenceForm.toDto(): PreferenceFormDto = PreferenceFormDto(
    housingType = housingType,
    hasOutdoorSpace = hasOutdoorSpace,
    activityLevel = activityLevel,
    preferredSpecies = preferredSpecies,
    preferredSize = preferredSize,
    experienceLevel = experienceLevel,
    householdNotes = householdNotes
)

fun PetDto.toDomain(): Pet = Pet(
    id = id,
    name = name,
    species = species,
    breed = breed,
    age = age,
    size = size,
    description = description,
    shelterName = shelterName,
    imageUrl = imageUrl
)
