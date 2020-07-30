package com.android.feedpoc.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Feed(
    @PrimaryKey(autoGenerate = true)
    var feedId: Long,
    var countryId: Long,
    var title: String,
    var imageHref: String,
    var description: String
) : Parcelable