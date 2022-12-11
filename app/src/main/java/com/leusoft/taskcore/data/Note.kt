package com.leusoft.taskcore.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val noteTitle: String,
    val noteDescription: String
) : Parcelable
