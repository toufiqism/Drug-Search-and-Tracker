package com.example.drugsearchandtracker.domain.repository

import com.example.drugsearchandtracker.data.local.dao.MedicationDao
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository interface for medication-related operations.
 * Defines the contract for medication data access and manipulation.
 */
interface MedicationRepository {
    /**
     * Retrieves all medications for a specific user.
     *
     * @param userId The ID of the user whose medications to retrieve
     * @return Flow of list of medications belonging to the user
     */
    fun getMedicationsByUserId(userId: String): Flow<List<MedicationEntity>>

    /**
     * Inserts a new medication into the database.
     *
     * @param medication The medication entity to insert
     */
    suspend fun insertMedication(medication: MedicationEntity)

    /**
     * Deletes a medication from the database.
     *
     * @param medication The medication entity to delete
     */
    suspend fun deleteMedication(medication: MedicationEntity)

    /**
     * Updates an existing medication in the database.
     *
     * @param medication The medication entity to update
     */
    suspend fun updateMedication(medication: MedicationEntity)

    /**
     * Retrieves a specific medication by its ID and user ID.
     *
     * @param id The ID of the medication to retrieve
     * @param userId The ID of the user who owns the medication
     * @return The medication entity if found, null otherwise
     */
    suspend fun getMedicationById(id: Long, userId: String): MedicationEntity?
}

/**
 * Implementation of MedicationRepository interface.
 * Handles all medication-related data operations using Room database.
 *
 * @property medicationDao Data Access Object for medication operations
 */
class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao
) : MedicationRepository {
    /**
     * @see MedicationRepository.getMedicationsByUserId
     */
    override fun getMedicationsByUserId(userId: String): Flow<List<MedicationEntity>> {
        return medicationDao.getMedicationsByUserId(userId)
    }

    /**
     * @see MedicationRepository.insertMedication
     */
    override suspend fun insertMedication(medication: MedicationEntity) {
        medicationDao.insertMedication(medication)
    }

    /**
     * @see MedicationRepository.deleteMedication
     */
    override suspend fun deleteMedication(medication: MedicationEntity) {
        medicationDao.deleteMedication(medication)
    }

    /**
     * @see MedicationRepository.updateMedication
     */
    override suspend fun updateMedication(medication: MedicationEntity) {
        medicationDao.updateMedication(medication)
    }

    /**
     * @see MedicationRepository.getMedicationById
     */
    override suspend fun getMedicationById(id: Long, userId: String): MedicationEntity? {
        return medicationDao.getMedicationById(id, userId)
    }
} 