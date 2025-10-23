package com.example.matchpet.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel,
    onLoggedOut: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            onLoggedOut()
            viewModel.consumeLogout()
        }
    }

    ProfileScreen(
        state = uiState,
        onRefresh = viewModel::refreshProfile,
        onLogout = viewModel::logout
    )
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onRefresh: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> CircularProgressIndicator(modifier = modifier.padding(24.dp))
        state.error != null -> Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = state.error, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRefresh) { Text(text = "Reintentar") }
        }

        state.user != null -> Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = state.user.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(text = state.user.email)
                state.user.phone?.let { Text(text = "Teléfono: $it") }
                state.user.city?.let { Text(text = "Ciudad: $it") }
                state.user.preferences?.let { preferences ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Preferencias", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Vivienda: ${preferences.housingType}")
                    Text(text = "Espacio exterior: ${if (preferences.hasOutdoorSpace) "Sí" else "No"}")
                    Text(text = "Actividad: ${preferences.activityLevel}")
                    Text(text = "Especie preferida: ${preferences.preferredSpecies}")
                    Text(text = "Tamaño preferido: ${preferences.preferredSize}")
                    Text(text = "Experiencia: ${preferences.experienceLevel}")
                    preferences.householdNotes?.let { Text(text = "Notas: $it") }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onLogout) { Text(text = "Cerrar sesión") }
            }
        }

        else -> Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sin información de perfil")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRefresh) { Text(text = "Actualizar") }
        }
    }
}
