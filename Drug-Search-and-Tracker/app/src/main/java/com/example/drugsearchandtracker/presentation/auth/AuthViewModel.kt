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

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState())
    val loginState: StateFlow<AuthUiState> = _loginState

    private val _signupState = MutableStateFlow<AuthUiState>(AuthUiState())
    val signupState: StateFlow<AuthUiState> = _signupState

    fun signIn(email: String, password: String) {
        signInUseCase(email, password).onEach { result ->
            _loginState.value = when (result) {
                is AuthResult.Success -> AuthUiState(isSuccess = true)
                is AuthResult.Error -> AuthUiState(error = result.errorMessage)
                is AuthResult.Loading -> AuthUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun signUp(name: String, email: String, password: String) {
        signUpUseCase(name, email, password).onEach { result ->
            _signupState.value = when (result) {
                is AuthResult.Success -> AuthUiState(isSuccess = true)
                is AuthResult.Error -> AuthUiState(error = result.errorMessage)
                is AuthResult.Loading -> AuthUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun resetState() {
        _loginState.value = AuthUiState()
        _signupState.value = AuthUiState()
    }
} 