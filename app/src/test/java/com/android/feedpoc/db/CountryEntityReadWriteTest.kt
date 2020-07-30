package com.android.feedpoc.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.android.feedpoc.data.local.AppDatabase
import com.android.feedpoc.data.local.dao.FeedsDao
import com.android.feedpoc.data.model.Country
import com.android.feedpoc.data.model.Feed
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CountryEntityReadWriteTest {
    private lateinit var feedsDao: FeedsDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        feedsDao = db.getFeedsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val country = sampleData()
        feedsDao.insertCountry(country)

        val getCounty = feedsDao.getCountry()
        assertThat(getCounty?.title, equalTo(country.title))
        assertThat(getCounty?.rows?.size, equalTo(country.rows?.size))
    }

    private fun sampleData(): Country {
        var feeds = ArrayList<Feed>()
        feeds.add(Feed(0, 0, "Title", "", "Desc"))
        return Country(0, "Canada", feeds)
    }
}