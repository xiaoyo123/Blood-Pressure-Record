package com.example.blood_pressure_record

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val time: String,
    val systolic: Int,
    val diastolic: Int,
    val heartbeat: Int
)
