package com.android.feedpoc.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.feedpoc.data.local.AppDataBaseBuilder
import com.android.feedpoc.data.model.Country
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.data.network.NetworkApi
import com.android.feedpoc.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedRepository(val context: Context) {

    fun loadFeeds(): LiveData<Result> {
        val feedsLiveData = MutableLiveData<Result>()

        if (!Constants.isNetworkAvailable(context)) {
            fetchFromCache(feedsLiveData)
        } else {
            fetchFromNetwork(feedsLiveData)
        }

        return feedsLiveData
    }

    private fun fetchFromCache(feedsLiveData: MutableLiveData<Result>) {
        var cache = getDB().getFeedsDao().getCountry()

        feedsLiveData.value =
            cache?.let { Result.Success(it) } ?: Result.Failure("Network is not available")
    }

    private fun fetchFromNetwork(feedsLiveData: MutableLiveData<Result>) {
        NetworkApi().feeds().enqueue(object : Callback<Country> {
            override fun onFailure(call: Call<Country>, t: Throwable) {
                feedsLiveData.value = Result.Failure(t.toString())
            }

            override fun onResponse(
                call: Call<Country>,
                response: Response<Country>
            ) {
                if (response.isSuccessful) {
                    var country = response.body() as Country
                    feedsLiveData.value = Result.Success(country)
                    updateLocalDB(country)
                } else {
                    feedsLiveData.value = Result.Failure(response.toString())
                }
            }
        })
    }

    private fun updateLocalDB(country: Country) {
        getDB().getFeedsDao().apply {
            clear()
            insertCountry(country)
        }
    }

    private fun getDB() = AppDataBaseBuilder.getInstance(context)

}