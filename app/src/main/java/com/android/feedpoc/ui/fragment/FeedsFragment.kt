package com.android.feedpoc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.feedpoc.R
import com.android.feedpoc.data.model.FeedRow
import com.android.feedpoc.data.model.Feeds
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.ui.activity.FeedActivity
import com.android.feedpoc.ui.adapter.FeedsAdapter
import com.android.feedpoc.util.Constants
import com.android.feedpoc.viewmodel.FeedsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_feeds.view.*


class FeedsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val feedsViewModel: FeedsViewModel by viewModels()
    private lateinit var feedsAdapter: FeedsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.sr_feeds_parent).apply {
            setOnRefreshListener(this@FeedsFragment)
        }
        feedsRecyclerView(view)
        getFeeds()
        observeFeeds()
    }

    private fun getFeeds() {
        if (Constants.isNetworkAvailable(requireActivity()))
            feedsViewModel.loadFeeds()
        else {
            showError(getString(R.string.network_error))
        }
    }

    private fun feedsRecyclerView(view: View) {
        feedsAdapter = FeedsAdapter(requireContext()) {
            startFeedDetailsFragment(it)
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        view.rv_feeds.layoutManager = layoutManager
        view.rv_feeds.adapter = feedsAdapter
    }

    private fun startFeedDetailsFragment(item: FeedRow) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, FeedDetailFragment.newInstance(item))
            addToBackStack(null)
            commit()
        }

    }

    private fun observeFeeds() {
        val feedsObserver = Observer<Result> {
            handleFeedResponse(it)
        }
        feedsViewModel.feeds().observe(viewLifecycleOwner, feedsObserver)
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