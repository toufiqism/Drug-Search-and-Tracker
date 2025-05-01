package com.example.drugsearchandtracker.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.example.drugsearchandtracker.domain.repository.AuthRepository
import com.example.drugsearchandtracker.domain.repository.DuplicateMedicationException
import com.example.drugsearchandtracker.domain.repository.MedicationLimitExceededException
import com.example.drugsearchandtracker.domain.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing medication details and addition to user's list.
 * Handles the process of adding a medication and managing the UI state.
 *
 * @property repository Repository for medication-related operations
 * @property authRepository Repository for user-related operations
 */
@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    // UI state holder for medication operations
    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Initial)
    val uiState: StateFlow<DetailsUiState> = _uiState

    /**
     * Adds a medication to the user's list.
     * Verifies user authentication and associates the medication with the current user.
     * Handles duplicate medication cases.
     *
     * @param medication The medication entity to be added
     */
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
            } catch (e: DuplicateMedicationException) {
                _uiState.value = DetailsUiState.Error("This medication is already in your list")
            } catch (e: MedicationLimitExceededException) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Medication limit exceeded")
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Failed to add medication")
            }
        }
    }
}

/**
 * Sealed class representing different states of the medication details UI.
 */
sealed class DetailsUiState {
    /** Initial state before any operation */
    object Initial : DetailsUiState()
    /** Loading state during medication addition */
    object Loading : DetailsUiState()
    /** Success state after successful addition */
    object Success : DetailsUiState()
    /** Error state with error message */
    data class Error(val message: String) : DetailsUiState()
} 