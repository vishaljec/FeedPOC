package com.android.feedpoc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    var uid: Long,
    var title: String,
    val rows: List<Feed>? = null)
