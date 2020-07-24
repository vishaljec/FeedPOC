package com.android.feedpoc.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.feedpoc.R
import com.android.feedpoc.ui.fragment.FeedsFragment


class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        startFeedFragment()
    }

    private fun startFeedFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, FeedsFragment())
            commit()
        }
    }
}