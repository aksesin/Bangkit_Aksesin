package com.bangkit.aksesin.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Place(
    val desc: String? = "",

    val distanceMeters: Int = 0,

    val placeId: String? = "",

    val reference: String? = "",

    val address: String? = "",

    val geometry: Geometry? = null,

    val name: String? = ""
)

data class Geometry(
    val location: Location
)

@Parcelize
data class Location(
    val lat: Double = 0.0,

    val lng: Double = 0.0
) : Parcelable
