package com.devkanhaiya.bookreader.data.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Transport(
    var text: String? = null,
    var title: String? = null,
    var description: String? = null,
    var destinationDate: String? = null,
    var directory: String? = null,
    var language: Int? = null,
    var isDeletable: Boolean = true,
    var id: Long? = null,
    var types: Long? = null

) : Parcelable {
    constructor() : this(null)

}