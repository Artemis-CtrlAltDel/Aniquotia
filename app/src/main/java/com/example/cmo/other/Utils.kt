package com.example.cmo.other

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.fragment.app.Fragment
import com.example.cmo.data.local.pojo.Details
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.presentation.viewmodel.MainViewModel

fun bookmarkQuote(quote: Quote, viewModel: MainViewModel){
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

fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
    }
    return false
}