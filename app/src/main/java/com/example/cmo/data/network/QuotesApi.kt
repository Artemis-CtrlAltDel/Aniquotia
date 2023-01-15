package com.example.cmo.data.network

import com.example.cmo.data.local.pojo.Quote
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {

    // 10 random quotes :
    @GET("quotes")
    fun getQuotes(): Observable<Response<ArrayList<Quote>>>

    @GET("quotes/character")
    fun getQuotesByCharacter(
        @Query("name") name: String
    ): Observable<Response<ArrayList<Quote>>>

    @GET("quotes/anime")
    fun getQuotesByAnime(
        @Query("title") title: String
    ): Observable<Response<ArrayList<Quote>>>

    // 1 random quote :
    @GET("random")
    fun getRandomQuote(): Observable<Quote>

    @GET("random/character")
    fun getRandomQuoteByCharacter(
        @Query("name") name: String
    ): Observable<Quote>

    @GET("random/anime")
    fun getRandomQuoteByAnime(
        @Query("title") title: String
    ): Observable<Quote>

}