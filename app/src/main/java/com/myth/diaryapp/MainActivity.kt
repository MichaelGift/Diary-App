package com.myth.diaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.myth.diaryapp.database.RecordDatabase
import com.myth.diaryapp.repository.RecordRepository
import com.myth.diaryapp.viewmodel.RecordViewModel
import com.myth.diaryapp.viewmodel.RecordViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var recordViewModel: RecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()
    }
    private fun setUpViewModel(){
        val recordRepository = RecordRepository(RecordDatabase(this))

        val recordViewModelFactory = RecordViewModelFactory(application, recordRepository)

        recordViewModel = ViewModelProvider(
            this, recordViewModelFactory
        )[RecordViewModel::class.java]
    }
}