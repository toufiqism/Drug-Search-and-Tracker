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

/**
 * ViewModel responsible for handling medication search functionality.
 * Manages the search process and results display using the RxNav API.
 *
 * @property rxNavApi API service for searching medications
 */
@HiltViewModel
class SearchMedicationViewModel @Inject constructor(
    private val rxNavApi: RxNavApi
) : ViewModel() {

    // UI state holder for search results
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState

    /**
     * Performs a search for medications based on the provided query.
     * Filters results to show only Semantic Branded Drugs (SBD) and limits to 10 results.
     *
     * @param query The search term to look up medications
     */
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

/**
 * Sealed class representing different states of the search UI.
 */
sealed class SearchUiState {
    /** Initial state before any search is performed */
    object Initial : SearchUiState()
    /** Loading state while fetching search results */
    object Loading : SearchUiState()
    /** Empty state when no results are found */
    object Empty : SearchUiState()
    /** Success state containing list of found medications */
    data class Success(val medications: List<ConceptProperty>) : SearchUiState()
    /** Error state with error message */
    data class Error(val message: String) : SearchUiState()
} 