package com.android.feedpoc.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.android.feedpoc.R
import com.android.feedpoc.ui.fragment.FeedsFragment
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedFragmentTest {

 /*   @get:Rule
    val activityTestRule = ActivityTestRule(FragmentTestActivity::class.java)
    @Before
     fun setUp() {
        val fragment = FeedsFragment()
        activityTestRule.activity.setFragment(fragment)
    }*/

    @Test
    fun isFeedListVisibleOnAppLaunch() {
        onView(withId(R.id.rv_feeds)).check(matches(isDisplayed()))
    }

    @Test
    fun isErrorSeekBarVisibleOnAppLaunchWhenNoNetworkConnectivity() {
        onView(withText(CoreMatchers.endsWith("Your device is not connected to internet"))).check(
            matches(isDisplayed())
        )
    }
}