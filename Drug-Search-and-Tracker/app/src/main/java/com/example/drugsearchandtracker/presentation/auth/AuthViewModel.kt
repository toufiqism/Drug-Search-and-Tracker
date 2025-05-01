package com.example.drugsearchandtracker.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugsearchandtracker.domain.model.AuthResult
import com.example.drugsearchandtracker.domain.usecase.SignInUseCase
import com.example.drugsearchandtracker.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel responsible for handling authentication-related operations.
 * Manages user login and signup processes using Firebase Authentication.
 *
 * @property signInUseCase Use case for handling user sign-in operations
 * @property signUpUseCase Use case for handling user registration operations
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    // State holders for login and signup operations
    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState())
    val loginState: StateFlow<AuthUiState> = _loginState

    private val _signupState = MutableStateFlow<AuthUiState>(AuthUiState())
    val signupState: StateFlow<AuthUiState> = _signupState

    /**
     * Attempts to sign in a user with the provided credentials.
     * Updates loginState based on the authentication result.
     *
     * @param email User's email address
     * @param password User's password
     */
    fun signIn(email: String, password: String) {
        signInUseCase(email, password).onEach { result ->
            _loginState.value = when (result) {
                is AuthResult.Success -> AuthUiState(isSuccess = true)
                is AuthResult.Error -> AuthUiState(error = result.errorMessage)
                is AuthResult.Loading -> AuthUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Registers a new user with the provided information.
     * Updates signupState based on the registration result.
     *
     * @param name User's display name
     * @param email User's email address
     * @param password User's chosen password
     */
    fun signUp(name: String, email: String, password: String) {
        signUpUseCase(name, email, password).onEach { result ->
            _signupState.value = when (result) {
                is AuthResult.Success -> AuthUiState(isSuccess = true)
                is AuthResult.Error -> AuthUiState(error = result.errorMessage)
                is AuthResult.Loading -> AuthUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Resets both login and signup states to their initial values.
     * Used when navigating away from auth screens or after completing operations.
     */
    fun resetState() {
        _loginState.value = AuthUiState()
        _signupState.value = AuthUiState()
    }
} 