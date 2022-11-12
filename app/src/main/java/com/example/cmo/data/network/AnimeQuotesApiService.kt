package com.example.cmo.data.network

import com.example.cmo.data.network.pojo.AnimeQuoteApiResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeQuotesApiService {

    // 10 random quotes :

    @GET("quotes")
    fun getQuotes(): Observable<ArrayList<AnimeQuoteApiResponse>>

    @GET("quotes/character")
    fun getQuotesByCharacter(
        @Query("name") name: String
    ): Observable<ArrayList<AnimeQuoteApiResponse>>

    @GET("quotes/anime")
    fun getQuotesByAnime(
        @Query("title") title: String
    ): Observable<ArrayList<AnimeQuoteApiResponse>>

    // 1 random quote :

    @GET("random")
    fun getRandomQuote(): Observable<AnimeQuoteApiResponse>

    @GET("quotes/character")
    fun getRandomQuoteByCharacter(
        @Query("name") name: String
    ): Observable<AnimeQuoteApiResponse>

    @GET("quotes/anime")
    fun getRandomQuoteByAnime(
        @Query("title") title: String
    ): Observable<AnimeQuoteApiResponse>

}