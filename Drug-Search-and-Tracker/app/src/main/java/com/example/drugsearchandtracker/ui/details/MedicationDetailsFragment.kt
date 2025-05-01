package com.example.drugsearchandtracker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.example.drugsearchandtracker.presentation.details.DetailsUiState
import com.example.drugsearchandtracker.presentation.details.MedicationDetailsViewModel
import com.example.drugsearchandtracker.ui.common.AuthResultDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tofiq.drugsearchandtracker.R
import com.tofiq.drugsearchandtracker.databinding.FragmentMedicationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicationDetailsFragment : Fragment() {

    private var _binding: FragmentMedicationDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MedicationDetailsViewModel by viewModels()

    private val medicationName: String by lazy { arguments?.getString("medicationName") ?: "" }
    private val genericName: String by lazy { arguments?.getString("genericName") ?: "" }
    private val rxcui: String by lazy { arguments?.getString("rxcui") ?: "" }
    private val tty: String by lazy { arguments?.getString("tty") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupClickListeners()
        displayMedicationDetails()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state: DetailsUiState) {
        when (state) {
            is DetailsUiState.Loading -> {
                binding.addToListButton.isEnabled = false
                binding.addToListButton.text = "Adding..."
            }

            is DetailsUiState.Success -> {
                AuthResultDialog(requireContext()).showSuccess(
                    title = getString(R.string.lbl_success),
                    message = getString(R.string.msg_medication_added_to_list_successfully),
                    buttonText = getString(R.string.lbl_ok)
                ) {
                    findNavController().popBackStack()
                }
            }

            is DetailsUiState.Error -> {
                binding.addToListButton.isEnabled = true
                binding.addToListButton.text = "Add Medication to List"
                AuthResultDialog(requireContext()).showError(
                    title = getString(R.string.lbl_error),
                    message = state.message
                ) {

                }


            }

            else -> {
                binding.addToListButton.isEnabled = true
                binding.addToListButton.text = "Add Medication to List"
            }
        }
    }



    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.addToListButton.setOnClickListener {
            viewModel.addMedicationToList(
                MedicationEntity(
                    name = medicationName,
                    synonym = genericName,
                    rxCui = rxcui,
                    tty = tty
                )
            )
        }
    }

    private fun displayMedicationDetails() {
        binding.apply {
            medicationName.text = this@MedicationDetailsFragment.medicationName
            genericName.text = this@MedicationDetailsFragment.genericName
            rxcuiText.text = rxcui
            ttyText.text = tty
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 