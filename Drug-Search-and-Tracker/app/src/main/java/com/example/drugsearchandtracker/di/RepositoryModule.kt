package com.example.drugsearchandtracker.di

import com.example.drugsearchandtracker.data.local.dao.MedicationDao
import com.example.drugsearchandtracker.domain.repository.MedicationRepository
import com.example.drugsearchandtracker.domain.repository.MedicationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMedicationRepository(
        medicationDao: MedicationDao
    ): MedicationRepository {
        return MedicationRepositoryImpl(medicationDao)
    }
} 