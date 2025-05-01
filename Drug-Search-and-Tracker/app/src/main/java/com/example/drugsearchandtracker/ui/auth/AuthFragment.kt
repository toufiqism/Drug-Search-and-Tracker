package com.example.drugsearchandtracker.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tofiq.drugsearchandtracker.R
import com.tofiq.drugsearchandtracker.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {
    
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_auth_to_signup)
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_auth_to_login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 