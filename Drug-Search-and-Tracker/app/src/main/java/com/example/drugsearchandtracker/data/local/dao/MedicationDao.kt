package com.example.drugsearchandtracker.data.local.dao

import androidx.room.*
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medications WHERE userId = :userId ORDER BY createdAt DESC")
    fun getMedicationsByUserId(userId: String): Flow<List<MedicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationEntity)

    @Delete
    suspend fun deleteMedication(medication: MedicationEntity)

    @Update
    suspend fun updateMedication(medication: MedicationEntity)

    @Query("SELECT * FROM medications WHERE id = :id AND userId = :userId")
    suspend fun getMedicationById(id: Long, userId: String): MedicationEntity?
} 