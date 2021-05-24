package com.bangkit.aksesin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlaceDetailResponse(
    @field:SerializedName("result")
    val result: Result
)

data class GeometryResponse(
    @field:SerializedName("location")
    val location: LocationResponse
)

data class LocationResponse(
    @field:SerializedName("lat")
    val lat: Double = 0.0,

    @field:SerializedName("lng")
    val lng: Double = 0.0
)

data class Result(
    @field:SerializedName("formatted_address")
    val address: String? = "",

    @field:SerializedName("geometry")
    val geometry: GeometryResponse,

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("place_id")
    val placeId: String? = "",
)