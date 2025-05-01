package com.example.drugsearchandtracker.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugsearchandtracker.presentation.search.SearchMedicationViewModel
import com.example.drugsearchandtracker.presentation.search.SearchUiState
import com.example.drugsearchandtracker.ui.common.Utils
import com.tofiq.drugsearchandtracker.R
import com.tofiq.drugsearchandtracker.databinding.FragmentSearchMedicationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMedicationFragment : Fragment() {

    private var _binding: FragmentSearchMedicationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchMedicationViewModel by viewModels()
    private lateinit var searchAdapter: SearchResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMedicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        setupSearchInput()
        observeUiState()
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchResultsAdapter { medication ->
            val bundle = Bundle().apply {
                putString("medicationName", medication.name)
                putString("genericName", medication.synonym)
                putString("rxcui", medication.rxcui)
                putString("tty", medication.tty)
            }
            findNavController().navigate(
                R.id.medicationDetailsFragment,
                bundle
            )
        }

        binding.searchResultsRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text?.toString() ?: ""
            viewModel.searchMedications(query)
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener { text ->
            viewModel.searchMedications(text?.toString() ?: "")
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUiState(state)
                }
            }
        }
    }

    private fun updateUiState(state: SearchUiState) {
        binding.apply {
            loadingProgressBar.isVisible = state is SearchUiState.Loading
            searchResultsRecyclerView.isVisible = state is SearchUiState.Success
            resultsLabel.isVisible = state is SearchUiState.Success
            emptyStateText.isVisible = state is SearchUiState.Empty

            when (state) {
                is SearchUiState.Success -> {
                    searchAdapter.submitList(state.medications)
                }
                is SearchUiState.Error -> {
                    emptyStateText.text = state.message
                    emptyStateText.isVisible = true
                }
                else -> Unit
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 