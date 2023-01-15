package com.example.cmo.data.local.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "details_table")
data class Details(

    @PrimaryKey(autoGenerate = false)
    val id: String,
    val anilistId: String,
    val malId: String,

    val title: String,
    val description: String,

    val image: String,
    val cover: String,

    val rating: String,
    val releaseDate: String,

    val genres: ArrayList<String>,

    val status: String,

    val mappings: DetailsMappings
): Serializable