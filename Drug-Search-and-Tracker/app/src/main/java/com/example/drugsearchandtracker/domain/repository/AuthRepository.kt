package com.example.drugsearchandtracker.domain.repository

import com.example.drugsearchandtracker.domain.model.AuthResult
import com.example.drugsearchandtracker.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(name: String, email: String, password: String): Flow<AuthResult<User>>
    fun signIn(email: String, password: String): Flow<AuthResult<User>>
    fun getCurrentUser(): User?
    suspend fun signOut()
} 