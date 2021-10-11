package com.devkanhaiya.bookreader.data.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Transport(
    val text: String,
    val title: String,
    val description: String,
    val destinationDate: String,
    val directory: String,
    val language: Int

) : Parcelable