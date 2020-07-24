package com.android.feedpoc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.feedpoc.R
import com.android.feedpoc.data.model.FeedRow
import com.android.feedpoc.glide.GlideApp
import kotlinx.android.synthetic.main.feed_row.view.*

class FeedsAdapter(private val context: Context, private val listener: (FeedRow) -> Unit) :
    RecyclerView.Adapter<FeedsAdapter.FeedsViewHolder>() {

    private var feedsList: ArrayList<FeedRow> = ArrayList()

    class FeedsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(feed: FeedRow, context: Context) {
            itemView.tv_title.text = feed.title
            itemView.tv_desc.text = feed.description
            GlideApp.with(context)
                .load(feed.imageHref)
                .override(250, 250)
                .placeholder(R.mipmap.ic_launcher)
                .into(itemView.iv_feed_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_row, parent, false)
        return FeedsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feedsList.size
    }

    override fun onBindViewHolder(holder: FeedsViewHolder, position: Int) {
        holder.bindItems(feedsList[position], context)
        holder.itemView.setOnClickListener { listener(feedsList[position]) }
    }

    fun updateFeedList(feedsList: ArrayList<FeedRow>) {
        this.feedsList = feedsList
        notifyDataSetChanged()
    }
}