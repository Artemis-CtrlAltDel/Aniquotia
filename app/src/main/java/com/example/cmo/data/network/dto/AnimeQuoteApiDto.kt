package com.example.cmo.data.network.dto

data class AnimeQuoteApiDto(
    val anime: String,
    val character: String,
    val quote: String,

    var bookmark: Boolean = false,
    var bookmarkCount: Long = 0
)