package com.leusoft.taskcore.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "event_table")
data class Event (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val eventTitle: String,
    val eventDescription: String?,
    val startDate: String?,
    val endDate : String?,
    val invitedName: String?,
    val invitedEmail: String?
) : Parcelable
