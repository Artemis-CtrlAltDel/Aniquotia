package com.example.cmo.data.network

import com.example.cmo.data.local.pojo.AnimeQuote
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeQuoteApi {

    // 10 random quotes :
    @GET("quotes")
    fun getQuotes(): Observable<Response<ArrayList<AnimeQuote>>>

    @GET("quotes/character")
    fun getQuotesByCharacter(
        @Query("name") name: String
    ): Observable<Response<ArrayList<AnimeQuote>>>

    @GET("quotes/anime")
    fun getQuotesByAnime(
        @Query("title") title: String
    ): Observable<Response<ArrayList<AnimeQuote>>>

    // 1 random quote :
    @GET("random")
    fun getRandomQuote(): Observable<AnimeQuote>

    @GET("random/character")
    fun getRandomQuoteByCharacter(
        @Query("name") name: String
    ): Observable<AnimeQuote>

    @GET("random/anime")
    fun getRandomQuoteByAnime(
        @Query("title") title: String
    ): Observable<AnimeQuote>

}