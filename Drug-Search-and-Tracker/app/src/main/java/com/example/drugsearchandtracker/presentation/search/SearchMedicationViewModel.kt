package com.example.drugsearchandtracker.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugsearchandtracker.data.remote.api.RxNavApi
import com.example.drugsearchandtracker.data.remote.model.ConceptProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMedicationViewModel @Inject constructor(
    private val rxNavApi: RxNavApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState

    fun searchMedications(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Initial
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = SearchUiState.Loading
                val response = rxNavApi.searchDrugs(query)
                
                val medications = response.drugGroup.conceptGroup
                    ?.filter { it.tty == "SBD" }
                    ?.flatMap { it.conceptProperties ?: emptyList() }
                    ?.take(10)
                    ?: emptyList()

                _uiState.value = if (medications.isEmpty()) {
                    SearchUiState.Empty
                } else {
                    SearchUiState.Success(medications)
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}

sealed class SearchUiState {
    object Initial : SearchUiState()
    object Loading : SearchUiState()
    object Empty : SearchUiState()
    data class Success(val medications: List<ConceptProperty>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
} 