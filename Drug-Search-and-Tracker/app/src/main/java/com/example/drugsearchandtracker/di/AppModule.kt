package com.example.drugsearchandtracker.di

import com.example.drugsearchandtracker.data.repository.FirebaseAuthRepository
import com.example.drugsearchandtracker.domain.manager.AuthManager
import com.example.drugsearchandtracker.domain.manager.AuthManagerImpl
import com.example.drugsearchandtracker.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthManager(auth: FirebaseAuth): AuthManager {
        return AuthManagerImpl(auth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return FirebaseAuthRepository(auth)
    }
} 