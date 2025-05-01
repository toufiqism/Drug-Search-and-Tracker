package com.example.drugsearchandtracker.data.repository

import com.example.drugsearchandtracker.domain.model.AuthResult
import com.example.drugsearchandtracker.domain.model.User
import com.example.drugsearchandtracker.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun signUp(name: String, email: String, password: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading())
            
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            
            result.user?.let { firebaseUser ->
                // Update display name
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                
                firebaseUser.updateProfile(profileUpdates).await()
                
                val user = User(
                    uid = firebaseUser.uid,
                    email = email,
                    name = name
                )
                trySend(AuthResult.Success(user))
            }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e.message ?: "An unknown error occurred"))
        }
        
        awaitClose()
    }

    override fun signIn(email: String, password: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading())
            
            val result = auth.signInWithEmailAndPassword(email, password).await()
            
            result.user?.let { firebaseUser ->
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName
                )
                trySend(AuthResult.Success(user))
            }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e.message ?: "An unknown error occurred"))
        }
        
        awaitClose()
    }

    override fun getCurrentUser(): User? {
        return auth.currentUser?.let { firebaseUser ->
            User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = firebaseUser.displayName
            )
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
} 