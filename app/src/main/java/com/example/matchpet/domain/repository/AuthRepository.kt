package com.example.matchpet.domain.repository

import com.example.matchpet.core.util.ResultState
import com.example.matchpet.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): ResultState<User>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String?,
        city: String?
    ): ResultState<User>

    suspend fun getProfile(): ResultState<User>
    suspend fun logout()
}
