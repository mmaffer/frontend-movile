package com.example.matchpet.data.remote.api

import com.example.matchpet.data.remote.dto.AuthResponseDto
import com.example.matchpet.data.remote.dto.LoginRequestDto
import com.example.matchpet.data.remote.dto.MatchRequestDto
import com.example.matchpet.data.remote.dto.MatchResponseDto
import com.example.matchpet.data.remote.dto.PreferenceFormDto
import com.example.matchpet.data.remote.dto.RegisterRequestDto
import com.example.matchpet.data.remote.dto.UserProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MatchPetApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResponseDto

    @GET("adopters/me")
    suspend fun getProfile(): UserProfileDto

    @PUT("adopters/me/preferences")
    suspend fun updatePreferences(@Body request: PreferenceFormDto): UserProfileDto

    @POST("adopters/match")
    suspend fun getMatches(@Body request: MatchRequestDto): MatchResponseDto
}
