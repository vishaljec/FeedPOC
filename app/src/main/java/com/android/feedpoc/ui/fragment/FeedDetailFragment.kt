package com.android.feedpoc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.feedpoc.R
import com.android.feedpoc.data.model.FeedRow
import com.android.feedpoc.glide.GlideApp
import com.android.feedpoc.ui.activity.FeedActivity

private const val FEED_ARG = "feed"

class FeedDetailFragment : Fragment() {

    private lateinit var feed: FeedRow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            feed = it?.getParcelable(FEED_ARG)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_desc).text = feed.description
        val feedImageView = view.findViewById<ImageView>(R.id.iv_feed_image)
        loadFeedImage(feedImageView)
        updateTitle()
    }

    private fun updateTitle() {
        (requireActivity() as FeedActivity).supportActionBar?.title = feed.title
    }

    private fun loadFeedImage(feedImageView: ImageView) {
        GlideApp.with(requireActivity())
            .load(feed.imageHref)
            .placeholder(R.mipmap.ic_launcher)
            .into(feedImageView)
    }

    companion object {
        @JvmStatic
        fun newInstance(feed: FeedRow) =
            FeedDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FEED_ARG, feed)

                }
            }
    }
}
