package com.example.matchpet.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SESSION_DATA_STORE = "matchpet_session"
private val Context.dataStore by preferencesDataStore(name = SESSION_DATA_STORE)

class SessionPreferences(private val context: Context) {

    private val tokenKey = stringPreferencesKey("jwt_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")

    val accessToken: Flow<String?> = context.dataStore.data.map { preferences: Preferences ->
        preferences[tokenKey]
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String?) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = accessToken
            if (refreshToken != null) {
                preferences[refreshTokenKey] = refreshToken
            }
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
            preferences.remove(refreshTokenKey)
        }
    }
}
