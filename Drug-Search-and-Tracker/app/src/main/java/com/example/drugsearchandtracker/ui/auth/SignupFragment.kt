package com.example.drugsearchandtracker.ui.auth

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
import com.example.drugsearchandtracker.presentation.auth.AuthViewModel
import com.example.drugsearchandtracker.ui.common.AuthResultDialog
import com.tofiq.drugsearchandtracker.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {
    
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var authResultDialog: AuthResultDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        authResultDialog = AuthResultDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
        observeAuthState()
    }

    private fun setupClickListeners() {
        binding.createAccountButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.signUp(name, email, password)
        }
    }

    private fun observeAuthState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signupState.collect { state ->
                    // Handle loading state
                    binding.createAccountButton.isEnabled = !state.isLoading
                    binding.progressBar.isVisible = state.isLoading
                    
                    // Handle error
                    state.error?.let { error ->
                        authResultDialog.showError(
                            title = "Registration Failed",
                            message = error,
                            onDismiss = { viewModel.resetState() }
                        )
                    }
                    
                    // Handle success
                    if (state.isSuccess) {
                        authResultDialog.showSuccess(
                            title = "Welcome!",
                            message = "Your account has been created successfully.",
                            buttonText = "Continue",
                            onDismiss = {
                                viewModel.resetState()
                                findNavController().popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 