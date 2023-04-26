package com.myth.diaryapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "records")
@Parcelize
data class DiaryRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recordTitle: String,
    val recordBody: String,
    val recordTimestamp: String
) : Parcelable