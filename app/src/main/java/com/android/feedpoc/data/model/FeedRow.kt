package com.android.feedpoc.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FeedRow(val title: String, val imageHref: String, val description: String) : Parcelable