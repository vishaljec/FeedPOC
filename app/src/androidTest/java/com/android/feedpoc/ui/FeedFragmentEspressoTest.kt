package com.android.feedpoc.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.feedpoc.R
import com.android.feedpoc.ui.fragment.FeedsFragment
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FeedFragmentEspressoTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<FeedsFragment>()
    }

    @Test
    fun isFeedListVisibleOnAppLaunch() {
        onView(withId(R.id.rv_feeds)).check(matches(isDisplayed()))
    }

    @Test
    fun isErrorTextVisibleOnAppLaunchWhenNoNetworkConnectivity() {
        onView(withId(R.id.errorText)).check(
            matches(isDisplayed())
        )
    }
}