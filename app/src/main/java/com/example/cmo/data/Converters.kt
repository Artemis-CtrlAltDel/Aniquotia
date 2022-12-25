package com.example.cmo.data

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun timestampToDate(time: Long?): Date? =
        time?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? =
        date?.time
}