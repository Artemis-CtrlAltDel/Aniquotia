package com.example.cmo.data.local.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "quotes_table")
data class Quote(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val anime: String,
    val character: String,
    val quote: String,

    var isBookmarked: Boolean = false,
    var bookmarkCount: Long = 0
): Serializable