package com.example.drugsearchandtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,
    val rxCui: String,
    val synonym: String,
    val tty: String,

    val createdAt: Long = System.currentTimeMillis()
) 