package com.devkanhaiya.bookreader.data.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Transport(
    val id: String,
    val title: String,
    val description: String,
    val destinationDate: String,
    val directory: String

) : Parcelable