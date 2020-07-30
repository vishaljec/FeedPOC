package com.android.feedpoc.data.local

import android.content.Context
import androidx.room.Room

object AppDataBaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(AppDatabase::class) {
            val instance = buildRoomDB(context)
            INSTANCE = instance
            return instance
        }

    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "feeds.db"
        ).allowMainThreadQueries().build()
}
