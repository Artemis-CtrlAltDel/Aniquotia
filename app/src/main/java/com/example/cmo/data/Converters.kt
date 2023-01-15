package com.example.cmo.data

import androidx.room.TypeConverter
import com.example.cmo.data.local.pojo.DetailsMappings
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class Converters {

    @TypeConverter
    fun jsonToList(value: String): ArrayList<String> =
        Gson().fromJson(value, Array<String>::class.java).toList() as ArrayList<String>

    @TypeConverter
    fun listToJson(value: ArrayList<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToClass(value: String): DetailsMappings =
        Gson().fromJson(value, DetailsMappings::class.java)

    @TypeConverter
    fun classToJson(value: DetailsMappings): String = Gson().toJson(value)

    @TypeConverter
    fun timestampToDate(time: Long?): Date? =
        time?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? =
        date?.time
}