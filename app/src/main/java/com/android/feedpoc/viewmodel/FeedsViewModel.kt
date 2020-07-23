package com.android.feedpoc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.data.repository.FeedRepository

class FeedsViewModel : ViewModel() {

    private var feedsLiveData = FeedRepository().loadFeeds()

    fun loadFeeds() {
        FeedRepository().loadFeeds()
    }

    fun getFeeds(): LiveData<Result> {
        return feedsLiveData
    }

}