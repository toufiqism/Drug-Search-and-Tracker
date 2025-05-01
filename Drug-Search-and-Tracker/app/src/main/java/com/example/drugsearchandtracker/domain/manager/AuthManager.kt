package com.example.drugsearchandtracker.domain.manager

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

interface AuthManager {
    val isUserLoggedIn: Flow<Boolean>
    suspend fun logout()
}

@Singleton
class AuthManagerImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthManager {

    override val isUserLoggedIn: Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun logout() {
        auth.signOut()
    }
} 