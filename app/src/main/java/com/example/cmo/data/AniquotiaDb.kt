package com.example.cmo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cmo.data.local.DetailsDao
import com.example.cmo.data.local.pojo.Details
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.data.local.QuotesDao

@Database(
    entities = [Details::class, Quote::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AniquotiaDb : RoomDatabase() {

    abstract fun animeQuoteDao(): QuotesDao
    abstract fun animeDetailsDao(): DetailsDao
}