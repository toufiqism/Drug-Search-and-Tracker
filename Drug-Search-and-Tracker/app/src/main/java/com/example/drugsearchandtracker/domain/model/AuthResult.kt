package com.example.drugsearchandtracker.domain.model

sealed class AuthResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T): AuthResult<T>(data = data)
    class Error<T>(errorMessage: String): AuthResult<T>(errorMessage = errorMessage)
    class Loading<T>: AuthResult<T>()
} 