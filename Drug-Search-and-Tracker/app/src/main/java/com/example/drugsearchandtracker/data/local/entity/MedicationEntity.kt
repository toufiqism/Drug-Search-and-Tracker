package com.example.drugsearchandtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dosage: String,
    val frequency: String,
    val startDate: Long,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) 