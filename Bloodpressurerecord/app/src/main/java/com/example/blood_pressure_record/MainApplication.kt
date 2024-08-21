package com.example.blood_pressure_record

import android.app.Application
import androidx.room.Room

class MainApplication : Application() {
    companion object{
        lateinit var recordDatabase: RecordDatabase
    }

    override fun onCreate() {
        super.onCreate()
        recordDatabase = Room.databaseBuilder(
            applicationContext,
            RecordDatabase::class.java,
            "Record_DB"
        ).build()
    }
}