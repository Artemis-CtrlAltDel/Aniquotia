package com.example.cmo.data.local.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class Anime(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val poster: String
)