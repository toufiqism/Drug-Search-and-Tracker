package com.example.drugsearchandtracker.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.drugsearchandtracker.presentation.auth.AuthViewModel
import com.example.drugsearchandtracker.ui.common.AuthResultDialog
import com.example.drugsearchandtracker.ui.common.Utils
import com.tofiq.drugsearchandtracker.R
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

    private fun setKeyboardHideForScroll(view: View) {
        val rootLayout = view.findViewById<ConstraintLayout>(R.id.root_layout)
        val scrollView = view.findViewById<ScrollView>(R.id.scrollView)
        // Set the keyboard listener
        Utils.setKeyboardListener(rootLayout, scrollView)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKeyboardHideForScroll(view)
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
                            title = getString(R.string.lbl_login_failed),
                            message = error,
                            onDismiss = { viewModel.resetState() }
                        )
                    }

                    // Handle success
                    if (state.isSuccess) {
                        authResultDialog.showSuccess(
                            title = getString(R.string.lbl_welcome_back),
                            message = getString(R.string.msg_you_have_successfully_logged_in),
                            buttonText = getString(R.string.lbl_continue),
                            onDismiss = {
                                viewModel.resetState()
                                findNavController().navigate(R.id.action_login_to_dashboard)
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