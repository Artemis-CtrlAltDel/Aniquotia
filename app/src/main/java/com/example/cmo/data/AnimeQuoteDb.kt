package com.example.cmo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cmo.data.local.pojo.Anime
import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.data.local.AnimeQuoteDao

@Database(
    entities = [Anime::class, AnimeQuote::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnimeQuoteDb: RoomDatabase() {

    abstract fun animeQuoteDao(): AnimeQuoteDao
}