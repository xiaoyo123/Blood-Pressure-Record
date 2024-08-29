package com.example.blood_pressure_record

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Record)

    @Query("Update record Set date = :date, time = :time, systolic = :systolic, diastolic = :diastolic, heartbeat = :heartbeat Where id = :id")
    fun update(id: Int, date: String, time: String, systolic: Int, diastolic: Int, heartbeat: Int)

    @Query("Delete FROM record WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * from record WHERE id = :id")
    fun getRecord(id: Int): Record

    @Query("SELECT * from record ORDER BY id DESC")
    fun getAllRecord(): LiveData<List<Record>>
}