package com.example.cmo.data.repository

import com.example.cmo.data.local.DetailsDao
import com.example.cmo.data.network.DetailsApi
import com.example.cmo.data.network.dto.DetailsResponse
import com.example.cmo.other.Resource
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val api: DetailsApi,
    private val dao: DetailsDao
) {

    fun getAnimeDetailsByTitle(title: String) {
        api.getDetailsByTitle(title)
            .map {
                if (it.isSuccessful) {
                    val results = it.body()?.results

                    dao.insertDetails(*results!!.toTypedArray())

                    return@map Resource.Success(results)
                }
                return@map Resource.Error("There is no results")
            }
            .doOnError { Resource.Error<DetailsResponse>("Check your connectivity") }
        }
}