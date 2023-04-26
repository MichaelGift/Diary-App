package com.myth.diaryapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myth.diaryapp.model.DiaryRecord
import com.myth.diaryapp.repository.RecordRepository
import kotlinx.coroutines.launch

class RecordViewModel(
    app: Application, private val recordRepository: RecordRepository
) : AndroidViewModel(app) {
    fun addRecord(record: DiaryRecord) = viewModelScope.launch {
        recordRepository.insertRecord(record)
    }

    fun deleteRecord(record: DiaryRecord) = viewModelScope.launch {
        recordRepository.deleteRecord(record)
    }

    fun updateRecord(record: DiaryRecord) = viewModelScope.launch {
        recordRepository.updateRecord(record)
    }

    fun getAllRecords() = recordRepository.getAllRecords()
    fun searchRecords(query: String) = recordRepository.searchRecords(query)
}