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
import com.example.drugsearchandtracker.presentation.auth.AuthViewModel
import com.example.drugsearchandtracker.ui.common.AuthResultDialog
import com.tofiq.drugsearchandtracker.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var authResultDialog: AuthResultDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        authResultDialog = AuthResultDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
        observeAuthState()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.signIn(email, password)
        }
    }

    private fun observeAuthState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    // Handle loading state
                    binding.loginButton.isEnabled = !state.isLoading
                    binding.progressBar.isVisible = state.isLoading
                    
                    // Handle error
                    state.error?.let { error ->
                        authResultDialog.showError(
                            title = "Login Failed",
                            message = error,
                            onDismiss = { viewModel.resetState() }
                        )
                    }
                    
                    // Handle success
                    if (state.isSuccess) {
                        authResultDialog.showSuccess(
                            title = "Welcome Back!",
                            message = "You have successfully logged in.",
                            buttonText = "Continue",
                            onDismiss = {
                                viewModel.resetState()
                                // TODO: Navigate to main screen
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