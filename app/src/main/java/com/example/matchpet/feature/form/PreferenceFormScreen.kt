package com.example.matchpet.feature.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matchpet.domain.model.PreferenceForm

@Composable
fun PreferenceFormRoute(
    viewModel: PreferenceFormViewModel,
    onFormSaved: (PreferenceForm) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onFormSaved(uiState.form)
            viewModel.consumeSuccess()
        }
    }

    PreferenceFormScreen(
        state = uiState,
        onFormChange = viewModel::updateForm,
        onSubmit = viewModel::submitForm
    )
}

@Composable
fun PreferenceFormScreen(
    state: PreferenceFormUiState,
    onFormChange: ((PreferenceForm) -> PreferenceForm) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Tu estilo de vida", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.form.housingType,
            onValueChange = { value -> onFormChange { copy(housingType = value) } },
            label = { Text("Tipo de vivienda") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        PreferenceSwitch(
            checked = state.form.hasOutdoorSpace,
            onCheckedChange = { checked -> onFormChange { copy(hasOutdoorSpace = checked) } },
            label = "¿Tienes espacio exterior?"
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.form.activityLevel,
            onValueChange = { value -> onFormChange { copy(activityLevel = value) } },
            label = { Text("Nivel de actividad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.form.preferredSpecies,
            onValueChange = { value -> onFormChange { copy(preferredSpecies = value) } },
            label = { Text("Especie preferida") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.form.preferredSize,
            onValueChange = { value -> onFormChange { copy(preferredSize = value) } },
            label = { Text("Tamaño preferido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.form.experienceLevel,
            onValueChange = { value -> onFormChange { copy(experienceLevel = value) } },
            label = { Text("Experiencia con mascotas") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.form.householdNotes.orEmpty(),
            onValueChange = { value -> onFormChange { copy(householdNotes = value) } },
            label = { Text("Notas adicionales") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        state.error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = onSubmit,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text(text = "Guardar y obtener match")
            }
        }
    }
}

@Composable
private fun PreferenceSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
        )
    }
}
