package com.android.feedpoc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.data.repository.FeedRepository

class FeedsViewModel : ViewModel() {

    private var feedsLiveData: MutableLiveData<Result>

    init {
        feedsLiveData = MutableLiveData()
    }

    fun loadFeeds() {
        feedsLiveData = FeedRepository().loadFeeds()
    }

    fun feeds(): LiveData<Result> {
        return feedsLiveData
    }

}