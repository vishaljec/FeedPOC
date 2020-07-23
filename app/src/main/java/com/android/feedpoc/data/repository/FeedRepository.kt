package com.android.feedpoc.data.repository

import androidx.lifecycle.MutableLiveData
import com.android.feedpoc.data.model.Feeds
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.data.network.NetworkApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedRepository {
    private val feedsLiveData = MutableLiveData<Result>()

    fun loadFeeds(): MutableLiveData<Result> {
        NetworkApi().feeds().enqueue(object : Callback<Feeds> {
            override fun onFailure(call: Call<Feeds>, t: Throwable) {
                feedsLiveData.value = Result.Failure(t.toString())
            }

            override fun onResponse(call: Call<Feeds>, response: Response<Feeds>) {
                if (response.isSuccessful) {
                    feedsLiveData.value = Result.Success(response.body() as Feeds)
                } else {
                    feedsLiveData.value = Result.Failure(response.toString())
                }
            }
        })
        return feedsLiveData
    }
}