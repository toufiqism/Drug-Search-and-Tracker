package com.example.drugsearchandtracker.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.example.drugsearchandtracker.domain.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Initial)
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun addMedicationToList(medication: MedicationEntity) {
        viewModelScope.launch {
            try {
                _uiState.value = DetailsUiState.Loading
                repository.insertMedication(medication)
                _uiState.value = DetailsUiState.Success
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Failed to add medication")
            }
        }
    }
}

sealed class DetailsUiState {
    object Initial : DetailsUiState()
    object Loading : DetailsUiState()
    object Success : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
} 