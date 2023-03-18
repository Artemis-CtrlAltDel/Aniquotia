package com.example.cmo.other

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.cmo.R
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.presentation.ui.activities.MainActivity
import com.example.cmo.presentation.viewmodel.MainViewModel
import java.util.Date

fun replaceFragment(activity: MainActivity, fragment: Fragment, title: String? = null, subtitle: String? = null) {
    val transaction = activity.supportFragmentManager.beginTransaction()
    title?.let { activity.supportActionBar?.title = it }
    subtitle?.let { activity.supportActionBar?.subtitle = it }
    transaction.replace(R.id.frame, fragment).addToBackStack(null)
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
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        ) {
            return true
        }
    }
    return false
}

val formatter = Formatter()
fun Long.format() = formatter.format(this)
fun Date.format() = formatter.format(this)