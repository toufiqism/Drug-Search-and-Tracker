package com.example.drugsearchandtracker.domain.usecase

import com.example.drugsearchandtracker.domain.model.AuthResult
import com.example.drugsearchandtracker.domain.model.User
import com.example.drugsearchandtracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(name: String, email: String, password: String): Flow<AuthResult<User>> {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return flow { emit(AuthResult.Error("All fields must be filled")) }
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(AuthResult.Error("Invalid email format")) }
        }
        
        if (password.length < 6) {
            return flow { emit(AuthResult.Error("Password must be at least 6 characters")) }
        }
        
        if (name.length < 2) {
            return flow { emit(AuthResult.Error("Name must be at least 2 characters")) }
        }
        
        return repository.signUp(name, email, password)
    }
} 