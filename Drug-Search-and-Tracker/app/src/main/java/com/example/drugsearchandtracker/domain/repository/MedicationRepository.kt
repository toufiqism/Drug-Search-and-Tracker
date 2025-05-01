package com.example.drugsearchandtracker.domain.repository

import com.example.drugsearchandtracker.data.local.dao.MedicationDao
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MedicationRepository {
    fun getAllMedications(): Flow<List<MedicationEntity>>
    suspend fun insertMedication(medication: MedicationEntity)
    suspend fun deleteMedication(medication: MedicationEntity)
    suspend fun updateMedication(medication: MedicationEntity)
    suspend fun getMedicationById(id: Long): MedicationEntity?
}

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao
) : MedicationRepository {
    override fun getAllMedications(): Flow<List<MedicationEntity>> {
        return medicationDao.getAllMedications()
    }

    override suspend fun insertMedication(medication: MedicationEntity) {
        medicationDao.insertMedication(medication)
    }

    override suspend fun deleteMedication(medication: MedicationEntity) {
        medicationDao.deleteMedication(medication)
    }

    override suspend fun updateMedication(medication: MedicationEntity) {
        medicationDao.updateMedication(medication)
    }

    override suspend fun getMedicationById(id: Long): MedicationEntity? {
        return medicationDao.getMedicationById(id)
    }
} 