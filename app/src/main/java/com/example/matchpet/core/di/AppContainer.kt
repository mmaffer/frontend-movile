package com.example.matchpet.core.di

import android.content.Context
import com.example.matchpet.core.datastore.SessionPreferences
import com.example.matchpet.core.network.AuthInterceptor
import com.example.matchpet.core.network.NetworkModule
import com.example.matchpet.data.repository.AdoptionRepositoryImpl
import com.example.matchpet.data.repository.AuthRepositoryImpl
import com.example.matchpet.domain.repository.AdoptionRepository
import com.example.matchpet.domain.repository.AuthRepository
import okhttp3.OkHttpClient

class AppContainer(context: Context) {

    private val sessionPreferences = SessionPreferences(context)
    private val authInterceptor = AuthInterceptor(sessionPreferences)
    private val okHttpClient: OkHttpClient = NetworkModule.buildOkHttpClient(authInterceptor)
    private val matchPetApi = NetworkModule.provideApi(okHttpClient)

    private val authRepository: AuthRepository = AuthRepositoryImpl(matchPetApi, sessionPreferences)
    private val adoptionRepository: AdoptionRepository =
        AdoptionRepositoryImpl(matchPetApi)

    val viewModelFactory = MatchPetViewModelFactory(
        authRepository = authRepository,
        adoptionRepository = adoptionRepository,
        sessionPreferences = sessionPreferences
    )
}
