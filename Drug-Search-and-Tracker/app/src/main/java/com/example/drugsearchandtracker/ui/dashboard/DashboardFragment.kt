package com.example.drugsearchandtracker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugsearchandtracker.presentation.dashboard.DashboardViewModel
import com.example.drugsearchandtracker.presentation.dashboard.DashboardUiState
import com.example.drugsearchandtracker.presentation.dashboard.LogoutState
import com.example.drugsearchandtracker.ui.common.AuthResultDialog
import com.tofiq.drugsearchandtracker.R
import com.tofiq.drugsearchandtracker.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var medicationsAdapter: MedicationsAdapter
    private lateinit var authResultDialog: AuthResultDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        setupAuthResultDialog()
        observeUiState()
        observeLogoutState()
    }

    private fun setupAuthResultDialog() {
        authResultDialog = AuthResultDialog(requireContext())
    }

    private fun setupRecyclerView() {
        medicationsAdapter = MedicationsAdapter(
            onEditClick = { medication ->
                // TODO: Navigate to edit screen
            },
            onDeleteClick = { medication ->
                viewModel.deleteMedication(medication)
            }
        )

        binding.medicationsRecyclerView.apply {
            adapter = medicationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        binding.addMedicationButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_search)
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadMedications()
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
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

    private fun observeLogoutState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logoutState.collect { state ->
                    when (state) {
                        is LogoutState.Loading -> {
                            binding.logoutProgressBar.isVisible = true
                            binding.logoutButton.isEnabled = false
                        }
                        is LogoutState.Success -> {
                            binding.logoutProgressBar.isVisible = false
                            binding.logoutButton.isEnabled = true
                            findNavController().navigate(R.id.action_dashboard_to_auth)
                        }
                        is LogoutState.Error -> {
                            binding.logoutProgressBar.isVisible = false
                            binding.logoutButton.isEnabled = true
                            authResultDialog.showError(
                                title = "Logout Failed",
                                message = state.message
                            ){

                            }
                        }
                        LogoutState.Initial -> {
                            binding.logoutProgressBar.isVisible = false
                            binding.logoutButton.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun updateUiState(state: DashboardUiState) {
        binding.apply {
            loadingProgressBar.isVisible = state is DashboardUiState.Loading
            errorStateContainer.isVisible = state is DashboardUiState.Error
            emptyStateContainer.isVisible = state is DashboardUiState.Empty
            medicationsRecyclerView.isVisible = state is DashboardUiState.Success

            when (state) {
                is DashboardUiState.Success -> {
                    medicationsAdapter.submitList(state.medications)
                }
                is DashboardUiState.Error -> {
                    errorMessageText.text = state.message
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