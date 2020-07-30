package com.android.feedpoc.data.local

import androidx.room.TypeConverter
import com.android.feedpoc.data.model.Feed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun toFeedJson(feeds: List<Feed?>?): String? {
        if (feeds == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Feed?>?>() {}.type
        return gson.toJson(feeds, type)
    }

    @TypeConverter
    fun fromFeedJson(feedsJson: String?): List<Feed>? {
        if (feedsJson == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Feed?>?>() {}.type
        return gson.fromJson<List<Feed>>(feedsJson, type)
    }
}