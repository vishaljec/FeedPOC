package com.android.feedpoc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.android.feedpoc.data.model.Country

@Dao
interface FeedsDao {

    @Transaction
    @Query("SELECT * FROM country LIMIT 1")
    suspend fun getCountry(): Country?

    @Insert
      suspend fun insertCountry(country: Country)

    @Query("DELETE FROM Country")
     suspend fun clear()
}