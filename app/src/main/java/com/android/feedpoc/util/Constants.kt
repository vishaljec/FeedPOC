package com.android.feedpoc.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constants {
    const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}