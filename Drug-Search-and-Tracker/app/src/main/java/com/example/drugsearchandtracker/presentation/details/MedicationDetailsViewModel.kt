package com.example.drugsearchandtracker.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.example.drugsearchandtracker.domain.repository.AuthRepository
import com.example.drugsearchandtracker.domain.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Initial)
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun addMedicationToList(medication: MedicationEntity) {
        viewModelScope.launch {
            try {
                _uiState.value = DetailsUiState.Loading
                val currentUser = authRepository.getCurrentUser()
                if (currentUser == null) {
                    _uiState.value = DetailsUiState.Error("User not logged in")
                    return@launch
                }
                repository.insertMedication(medication.copy(userId = currentUser.uid))
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