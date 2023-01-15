package com.example.cmo.data.network.dto

import com.example.cmo.data.local.pojo.Details

data class DetailsResponse(

    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: ArrayList<Details>
)