package com.bangkit.aksesin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlaceDetailResponse(
    @field:SerializedName("result")
    val result: Result
)

data class Geometry(
    @field:SerializedName("location")
    val location: Location
)

data class Location(
    @field:SerializedName("lat")
    val lat: Double = 0.0,

    @field:SerializedName("lng")
    val lng: Double = 0.0
)

data class Result(
    @field:SerializedName("formatted_address")
    val address: String? = "",

    @field:SerializedName("geometry")
    val geometry: Geometry,

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("place_id")
    val placeId: String? = "",
)