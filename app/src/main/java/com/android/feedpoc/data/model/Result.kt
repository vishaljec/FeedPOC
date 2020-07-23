package com.android.feedpoc.data.model

sealed class Result {
    data class Success(val feeds: Feeds) : Result()
    data class Failure(val exception: String) : Result()
}