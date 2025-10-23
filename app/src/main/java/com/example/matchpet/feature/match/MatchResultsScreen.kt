package com.example.matchpet.feature.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.matchpet.domain.model.PreferenceForm

@Composable
fun MatchResultsRoute(
    viewModel: MatchResultsViewModel,
    form: PreferenceForm?
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(form) {
        form?.let { viewModel.loadMatches(it) }
    }

    MatchResultsScreen(uiState = uiState, missingForm = form == null)
}

@Composable
fun MatchResultsScreen(
    uiState: MatchResultsUiState,
    missingForm: Boolean,
    modifier: Modifier = Modifier
) {
    when {
        missingForm -> Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Completa el formulario para ver coincidencias")
        }

        uiState.isLoading -> Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        uiState.error != null -> Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = uiState.error, color = MaterialTheme.colorScheme.error)
        }

        uiState.result?.pets.isNullOrEmpty() -> Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Aún no hay coincidencias, intenta actualizar tus preferencias")
        }

        else -> LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.result!!.pets) { pet ->
                Surface(
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!pet.imageUrl.isNullOrBlank()) {
                            AsyncImage(
                                model = pet.imageUrl,
                                contentDescription = pet.name,
                                modifier = Modifier.size(72.dp)
                            )
                        } else {
                            PlaceholderIcon(icon = Icons.Default.Pets)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = pet.name, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = listOfNotNull(pet.species, pet.breed).joinToString(" • "),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            if (!pet.description.isNullOrBlank()) {
                                Text(
                                    text = pet.description,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            pet.shelterName?.let {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Refugio: $it",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderIcon(icon: ImageVector) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.size(72.dp),
        tonalElevation = 1.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(imageVector = icon, contentDescription = null)
        }
    }
}
