package com.example.blood_pressure_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordViewModel : ViewModel(){

    val recordDao = MainApplication.recordDatabase.recordDao()

    val recordList: LiveData<List<Record>> = recordDao.getAllRecord()
    val record: LiveData<Record> = MutableLiveData()

    fun insertRecord(date: String, time: String, systolic: Int, diastolic: Int, heartbeat: Int){
        viewModelScope.launch(Dispatchers.IO) {
            recordDao.insert(
                Record(
                    date = date, time = time,
                    systolic = systolic, diastolic = diastolic, heartbeat = heartbeat
                )
            )
        }
    }
    fun deleteRecord(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            recordDao.delete(id)
        }
    }
    fun updateRecord(id: Int, date: String, time: String, systolic: Int, diastolic: Int, heartbeat: Int){
        viewModelScope.launch(Dispatchers.IO){
            recordDao.update(id, date, time, systolic, diastolic, heartbeat)
        }
    }
}