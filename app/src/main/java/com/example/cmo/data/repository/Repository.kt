package com.example.cmo.data.repository

import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.data.local.AnimeQuoteDao
import com.example.cmo.data.network.AnimeQuoteApi
import com.example.cmo.other.Resource
import io.reactivex.rxjava3.core.Observable
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val animeQuoteApi: AnimeQuoteApi,
    private val animeQuoteDao: AnimeQuoteDao
) {

    /** API calls : **/
    // 10 random quotes
    fun getQuotes(): Observable<Resource<ArrayList<AnimeQuote>>> =
        animeQuoteApi.getQuotes()
            .map {
                if (it.isSuccessful) {
                    return@map Resource.Success(it.body()!!)
                }
                return@map Resource.Error("Something went wrong")
            }
            .doOnError { Resource.Error<ArrayList<AnimeQuote>>("Please check your application's connectivity") }

    fun getQuotesByCharacter(character: String): Observable<Resource<ArrayList<AnimeQuote>>> =
        animeQuoteApi.getQuotesByCharacter(character)
            .map {
                if (it.isSuccessful) {
                    return@map Resource.Success(it.body()!!)
                }
                return@map Resource.Error("Something went wrong")
            }
            .doOnError { Resource.Error<ArrayList<AnimeQuote>>("Please check your application's connectivity") }

    fun getQuotesByAnime(anime: String): Observable<Resource<java.util.ArrayList<AnimeQuote>>> =
        animeQuoteApi.getQuotesByCharacter(anime)
            .map {
                if (it.isSuccessful) {
                    return@map Resource.Success(it.body()!!)
                }
                return@map Resource.Error("Something went wrong")
            }
            .doOnError { Resource.Error<ArrayList<AnimeQuote>>("Please check your application's connectivity") }

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