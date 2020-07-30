package com.android.feedpoc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.feedpoc.R
import com.android.feedpoc.data.model.Country
import com.android.feedpoc.data.model.Feed
import com.android.feedpoc.data.model.Result
import com.android.feedpoc.ui.activity.FeedActivity
import com.android.feedpoc.ui.adapter.FeedsAdapter
import com.android.feedpoc.util.Constants
import com.android.feedpoc.viewmodel.FeedsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_feeds.*
import kotlinx.android.synthetic.main.fragment_feeds.view.*


class FeedsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

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
        observeFeeds()
    }

    private fun getFeeds() {
        if (Constants.isNetworkAvailable(requireActivity()))
         //feedsViewModel.loadFeeds()
        else {
            Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show()
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

    private fun startFeedDetailsFragment(item: Feed) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, FeedDetailFragment.newInstance(item))
            addToBackStack(null)
            commit()
        }

    }

    private fun observeFeeds() {
        val feedsViewModel: FeedsViewModel by activityViewModels()
        val feedsObserver = Observer<Result> {
            handleFeedResponse(it)
        }
        errorText.visibility = View.VISIBLE
        feedsViewModel.feeds().observe(viewLifecycleOwner, feedsObserver)
    }

    private fun updateTitle(title: String) {
        (requireActivity() as FeedActivity).supportActionBar?.title = title
    }

    private fun handleFeedResponse(feedResult: Result) {
        when (feedResult) {
            is Result.Success -> {
                showFeeds(feedResult.country)
            }
            is Result.Failure -> {
                showError(feedResult.exception)
            }
        }
    }

    private fun showFeeds(feed: Country) {
        feed.rows?.let {
            feedsAdapter.updateFeedList(it)
            updateTitle(feed.title)
        }

        errorText.visibility = View.GONE
    }

    private fun showError(error: String) {
        errorText.visibility = View.VISIBLE
        errorText.text = error
    }

    override fun onRefresh() {
        getFeeds()
        swipeRefreshLayout.isRefreshing = false
    }
}