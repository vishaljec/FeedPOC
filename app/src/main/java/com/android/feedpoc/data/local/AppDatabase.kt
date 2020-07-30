package com.android.feedpoc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.feedpoc.data.local.dao.FeedsDao
import com.android.feedpoc.data.model.Country
import com.android.feedpoc.data.model.Feed

@Database(entities = [Country::class, Feed::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFeedsDao(): FeedsDao
}