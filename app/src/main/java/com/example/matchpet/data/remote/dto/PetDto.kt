package com.example.matchpet.data.remote.dto

data class PetDto(
    val id: String,
    val name: String,
    val species: String,
    val breed: String?,
    val age: Int?,
    val size: String?,
    val description: String?,
    val shelterName: String?,
    val imageUrl: String?
)
