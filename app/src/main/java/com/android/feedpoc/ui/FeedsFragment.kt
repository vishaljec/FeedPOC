package com.android.feedpoc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.feedpoc.R
import com.android.feedpoc.data.model.Feeds
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.ui.adapter.FeedsAdapter
import com.android.feedpoc.util.Constants
import com.android.feedpoc.viewmodel.FeedsViewModel
import com.google.android.material.snackbar.Snackbar


class FeedsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val feedsViewModel: FeedsViewModel by viewModels()
    private lateinit var feedsAdapter: FeedsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var feedRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.sr_feeds_parent).apply {
            this.setOnRefreshListener(this@FeedsFragment)
        }
        feedRecyclerView = view.findViewById(R.id.rv_feeds)
        feedsRecyclerView()
        observeFeeds()
    }


    private fun getFeeds() {
        if (Constants.isNetworkAvailable(requireActivity()))
            feedsViewModel.loadFeeds()
        else {
            showError(getString(R.string.network_error))
        }
    }

    private fun feedsRecyclerView() {
        feedsAdapter = FeedsAdapter(requireActivity())
        feedRecyclerView.layoutManager = (LinearLayoutManager(requireActivity()))
        feedRecyclerView.adapter = feedsAdapter
    }

    private fun observeFeeds() {
        val feedsObserver = Observer<Result> {
            handleFeedResponse(it)
        }
        feedsViewModel.feeds().observe(requireActivity(), feedsObserver)
    }

    private fun updateTitle(title: String) {
        (requireActivity() as FeedActivity).supportActionBar?.title = title
    }

    private fun handleFeedResponse(feedResult: Result) {
        when (feedResult) {
            is Result.Success -> {
                showFeeds(feedResult.feeds)
            }
            is Result.Failure -> {
                showError(feedResult.exception)
            }
        }
    }

    private fun showFeeds(feed: Feeds) {
        feedsAdapter.updateFeedList(feed.rows)
        updateTitle(feed.title)
    }

    private fun showError(error: String) {
        Snackbar.make(swipeRefreshLayout, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        getFeeds()
        swipeRefreshLayout.isRefreshing = false
    }
}