package com.example.cmo.data.repository

import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.data.local.QuotesDao
import com.example.cmo.data.network.QuotesApi
import com.example.cmo.other.Resource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val api: QuotesApi,
    private val dao: QuotesDao
) {

    /** API calls : **/
    // 10 random quotes
    fun getQuotes(): Observable<Resource<ArrayList<Quote>>> =
        api.getQuotes()
            .map {
                if (it.isSuccessful) {
                    return@map Resource.Success(it.body()!!)
                }
                return@map Resource.Error("Something went wrong")
            }
            .doOnError { Resource.Error<ArrayList<Quote>>("Please check your application's connectivity") }

    fun getQuotesByCharacter(character: String): Observable<Resource<ArrayList<Quote>>> =
        api.getQuotesByCharacter(character)
            .map {
                if (it.isSuccessful) {
                    return@map Resource.Success(it.body()!!)
                }
                return@map Resource.Error("Something went wrong")
            }
            .doOnError { Resource.Error<ArrayList<Quote>>("Please check your application's connectivity") }

    fun getQuotesByAnime(anime: String): Observable<Resource<java.util.ArrayList<Quote>>> =
        api.getQuotesByCharacter(anime)
            .map {
                if (it.isSuccessful) {
                    return@map Resource.Success(it.body()!!)
                }
                return@map Resource.Error("Something went wrong")
            }
            .doOnError { Resource.Error<ArrayList<Quote>>("Please check your application's connectivity") }

    // 1 random quote
    fun getRandomQuote() =
        api.getRandomQuote()

    fun getRandomQuoteByCharacter(character: String) =
        api.getRandomQuoteByCharacter(character)

    fun getRandomQuoteByAnime(anime: String) =
        api.getRandomQuoteByAnime(anime)

    /** Database calls : **/
    fun insertQuote(quote: Quote) =
        dao.insertQuote(quote)

    fun deleteQuote(quote: Quote) =
        dao.deleteQuote(quote)

    fun getSavedQuotes() =
        dao.getQuotes()
}