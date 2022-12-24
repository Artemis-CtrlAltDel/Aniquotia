package com.example.cmo.data.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeQuotesApi {

    // 10 random quotes :

    @GET("quotes")
    fun getQuotes(): Observable<ArrayList<AnimeQuotesApiDto>>

    @GET("quotes/character")
    fun getQuotesByCharacter(
        @Query("name") name: String
    ): Observable<ArrayList<AnimeQuotesApiDto>>

    @GET("quotes/anime")
    fun getQuotesByAnime(
        @Query("title") title: String
    ): Observable<ArrayList<AnimeQuotesApiDto>>

    // 1 random quote :

    @GET("random")
    fun getRandomQuote(): Observable<AnimeQuotesApiDto>

    @GET("random/character")
    fun getRandomQuoteByCharacter(
        @Query("name") name: String
    ): Observable<AnimeQuotesApiDto>

    @GET("random/anime")
    fun getRandomQuoteByAnime(
        @Query("title") title: String
    ): Observable<AnimeQuotesApiDto>

}