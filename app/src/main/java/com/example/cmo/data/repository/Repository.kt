package com.example.cmo.data.repository

import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.data.local.AnimeQuoteDao
import com.example.cmo.data.network.AnimeQuoteApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val animeQuoteApi: AnimeQuoteApi,
    private val animeQuoteDao: AnimeQuoteDao
) {

    /** API calls : **/
    // 10 random quotes
    fun getQuotes() =
        animeQuoteApi.getQuotes()

    fun getQuotesByCharacter(character: String) =
        animeQuoteApi.getQuotesByCharacter(character)

    fun getQuotesByAnime(anime: String) =
        animeQuoteApi.getQuotesByCharacter(anime)

    // 1 random quote
    fun getRandomQuote() =
        animeQuoteApi.getRandomQuote()

    fun getRandomQuoteByCharacter(character: String) =
        animeQuoteApi.getRandomQuoteByCharacter(character)

    fun getRandomQuoteByAnime(anime: String) =
        animeQuoteApi.getRandomQuoteByAnime(anime)

    /** Database calls : **/
    fun insertQuote(quote: AnimeQuote) =
        animeQuoteDao.insertQuote(quote)

    fun deleteQuote(quote: AnimeQuote) =
        animeQuoteDao.deleteQuote(quote)

    fun getSavedQuotes() =
        animeQuoteDao.getQuotes()
}