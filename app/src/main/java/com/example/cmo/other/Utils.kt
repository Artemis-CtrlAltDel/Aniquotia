package com.example.cmo.other

import androidx.fragment.app.Fragment
import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.data.network.dto.AnimeQuoteApiDto
import com.example.cmo.presentation.viewmodel.MainViewModel

fun bookmarkQuote(quote: AnimeQuote, viewModel: MainViewModel){
    quote.isBookmarked = !quote.isBookmarked
    quote.bookmarkCount += if (quote.isBookmarked) 1 else -1

    when (quote.isBookmarked){
        true -> viewModel.insertQuote(quote)
        else -> viewModel.deleteQuote(quote)
    }
}

fun replaceFragment(parent: Int, fragment: Fragment){
    val transaction = fragment.parentFragmentManager.beginTransaction()
    transaction.replace(parent, fragment)
    transaction.commit()
}