package com.example.drugsearchandtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String? = null,
    val name: String,
    val synonym: String,
    val rxCui: String,
    val tty: String,
    val createdAt: Long = System.currentTimeMillis()
) 