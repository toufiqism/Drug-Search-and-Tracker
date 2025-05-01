package com.example.drugsearchandtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.drugsearchandtracker.data.local.dao.MedicationDao
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity

@Database(
    entities = [MedicationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao

    companion object {
        const val DATABASE_NAME = "drug_tracker_db"
    }
} 