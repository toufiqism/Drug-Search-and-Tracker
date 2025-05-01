package com.example.drugsearchandtracker.domain.usecase

import com.example.drugsearchandtracker.domain.model.AuthResult
import com.example.drugsearchandtracker.domain.model.User
import com.example.drugsearchandtracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult<User>> {
        if (email.isBlank() || password.isBlank()) {
            return flow { emit(AuthResult.Error("Email and password cannot be empty")) }
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(AuthResult.Error("Invalid email format")) }
        }
        
        if (password.length < 6) {
            return flow { emit(AuthResult.Error("Password must be at least 6 characters")) }
        }
        
        return repository.signIn(email, password)
    }
} 