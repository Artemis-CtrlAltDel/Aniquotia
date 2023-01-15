package com.example.cmo.data.network.dto

import com.example.cmo.data.local.pojo.Quote

data class QuotesResponse(
    val data: List<Quote>
)