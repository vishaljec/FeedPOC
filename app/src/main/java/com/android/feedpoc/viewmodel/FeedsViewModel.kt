package com.android.feedpoc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.feedpoc.data.model.Country
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.data.repository.FeedRepository
import kotlinx.coroutines.launch

class FeedsViewModel(application: Application) : AndroidViewModel(application) {

    private val feedRepository:FeedRepository = FeedRepository(application.applicationContext, viewModelScope)

    private var feedsLiveData = feedRepository.loadFeeds()


    fun feeds(): LiveData<Result> {
        return feedsLiveData
    }

    fun loadFeeds() {
        feedRepository.loadFeeds()
    }

}