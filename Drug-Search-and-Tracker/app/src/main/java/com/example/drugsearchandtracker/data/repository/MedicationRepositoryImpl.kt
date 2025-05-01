package com.example.drugsearchandtracker.data.repository

import com.example.drugsearchandtracker.data.local.dao.MedicationDao
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.example.drugsearchandtracker.domain.repository.DuplicateMedicationException
import com.example.drugsearchandtracker.domain.repository.MedicationLimitExceededException
import com.example.drugsearchandtracker.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao
) : MedicationRepository {

    override fun getMedicationsByUserId(userId: String): Flow<List<MedicationEntity>> {
        return medicationDao.getMedicationsByUserId(userId)
    }

    override suspend fun insertMedication(medication: MedicationEntity) {
        if (medicationDao.medicationExists(medication.rxCui, medication.userId!!)) {
            throw DuplicateMedicationException()
        }
        
        val currentCount = medicationDao.getMedicationCount(medication.userId!!)
        if (currentCount >= 3) {
            throw MedicationLimitExceededException()
        }
        
        medicationDao.insertMedication(medication)
    }

    override suspend fun deleteMedication(medication: MedicationEntity) {
        medicationDao.deleteMedication(medication)
    }

    override suspend fun updateMedication(medication: MedicationEntity) {
        medicationDao.updateMedication(medication)
    }

    override suspend fun getMedicationById(id: Long, userId: String): MedicationEntity? {
        return medicationDao.getMedicationById(id, userId)
    }

    override suspend fun medicationExists(rxCui: String, userId: String): Boolean {
        return medicationDao.medicationExists(rxCui, userId)
    }
} 