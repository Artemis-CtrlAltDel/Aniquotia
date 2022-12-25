package com.example.cmo.data.local.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_table")
data class AnimeQuote(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val anime: String,
    val character: String,
    val quote: String,

    var isBookmarked: Boolean = false,
    var bookmarkCount: Long = 0
)