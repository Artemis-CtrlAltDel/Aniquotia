package com.example.cmo.data.network

import com.example.cmo.data.network.dto.DetailsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {

    @GET("{title}")
    fun getDetailsByTitle(@Path("title") title: String): Observable<Response<DetailsResponse>>
}