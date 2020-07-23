package com.android.feedpoc.data.network


import com.android.feedpoc.data.model.Feeds
import com.android.feedpoc.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkApi {

    @GET("facts.json")
    fun feeds(): Call<Feeds>

    companion object {
        operator fun invoke(): NetworkApi {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
        }
    }
}