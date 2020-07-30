package com.android.feedpoc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.android.feedpoc.data.model.Country

@Dao
abstract class FeedsDao {

    @Transaction
    @Query("SELECT * FROM country LIMIT 1")
    abstract fun getCountry(): Country?

    @Insert
    abstract fun insertCountry(country: Country)

    @Query("DELETE FROM Country")
    abstract fun clear()
}