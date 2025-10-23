package com.example.matchpet.data.repository

import com.example.matchpet.core.datastore.SessionPreferences
import com.example.matchpet.core.util.ResultState
import com.example.matchpet.data.mapper.toDomain
import com.example.matchpet.data.remote.api.MatchPetApi
import com.example.matchpet.data.remote.dto.LoginRequestDto
import com.example.matchpet.data.remote.dto.RegisterRequestDto
import com.example.matchpet.domain.model.User
import com.example.matchpet.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: MatchPetApi,
    private val sessionPreferences: SessionPreferences
) : AuthRepository {
    override suspend fun login(email: String, password: String): ResultState<User> = try {
        val response = api.login(LoginRequestDto(email, password))
        sessionPreferences.saveTokens(response.accessToken, response.refreshToken)
        ResultState.success(response.user.toDomain())
    } catch (exception: Exception) {
        ResultState.error(exception.message ?: "Error al iniciar sesión")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String?,
        city: String?
    ): ResultState<User> = try {
        val response = api.register(
            RegisterRequestDto(
                name = name,
                email = email,
                password = password,
                phone = phone,
                city = city
            )
        )
        sessionPreferences.saveTokens(response.accessToken, response.refreshToken)
        ResultState.success(response.user.toDomain())
    } catch (exception: Exception) {
        ResultState.error(exception.message ?: "Error al registrar usuario")
    }

    override suspend fun getProfile(): ResultState<User> = try {
        ResultState.success(api.getProfile().toDomain())
    } catch (exception: Exception) {
        ResultState.error(exception.message ?: "No se pudo obtener el perfil")
    }

    override suspend fun logout() {
        sessionPreferences.clearSession()
    }
}
