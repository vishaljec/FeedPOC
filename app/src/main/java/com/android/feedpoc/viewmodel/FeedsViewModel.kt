package com.android.feedpoc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.data.repository.FeedRepository

class FeedsViewModel(application: Application) : AndroidViewModel(application) {

    private var feedsLiveData =
        FeedRepository(application.applicationContext).loadFeeds()


    fun feeds(): LiveData<Result> {
        return feedsLiveData
    }
}