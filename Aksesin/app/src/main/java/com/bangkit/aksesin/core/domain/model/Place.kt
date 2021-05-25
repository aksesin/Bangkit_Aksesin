package com.bangkit.aksesin.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Place(
    val locationName: String? = "",

    val distanceMeters: Int = 0,

    val placeId: String? = "",

    val reference: String? = "",

    var address: String? = "",

    var geometry: Geometry? = null,

    var name: String? = ""
)

data class Geometry(
    val location: Location
)

@Parcelize
data class Location(
    val lat: Double = 0.0,

    val lng: Double = 0.0
) : Parcelable
