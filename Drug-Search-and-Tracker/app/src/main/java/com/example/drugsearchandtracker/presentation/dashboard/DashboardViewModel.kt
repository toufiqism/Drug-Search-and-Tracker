package com.example.drugsearchandtracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.example.drugsearchandtracker.domain.manager.AuthManager
import com.example.drugsearchandtracker.domain.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Initial)
    val logoutState: StateFlow<LogoutState> = _logoutState

    init {
        loadMedications()
    }

    fun loadMedications() {
        viewModelScope.launch {
            repository.getAllMedications()
                .catch { error ->
                    _uiState.value = DashboardUiState.Error(error.message ?: "Unknown error occurred")
                }
                .collect { medications ->
                    _uiState.value = if (medications.isEmpty()) {
                        DashboardUiState.Empty
                    } else {
                        DashboardUiState.Success(medications)
                    }
                }
        }
    }

    fun deleteMedication(medication: MedicationEntity) {
        viewModelScope.launch {
            try {
                repository.deleteMedication(medication)
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Failed to delete medication")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _logoutState.value = LogoutState.Loading
                authManager.logout()
                _logoutState.value = LogoutState.Success
            } catch (e: Exception) {
                _logoutState.value = LogoutState.Error("Failed to logout")
            }
        }
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    object Empty : DashboardUiState()
    data class Success(val medications: List<MedicationEntity>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

sealed class LogoutState {
    object Initial : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
} 