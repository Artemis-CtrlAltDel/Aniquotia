package com.example.cmo.data.local.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailsMappings(

    val mal: String,
    val anidb: String,
    val kitsu: String,
    val anilist: String,
    val thetvdb: String,
    val anisearch: String,
    val livechart: String,

    @SerializedName("notify.moe")
    val notifymoe: String,
    @SerializedName("anime-planet")
    val animeplanet: String
): Serializable