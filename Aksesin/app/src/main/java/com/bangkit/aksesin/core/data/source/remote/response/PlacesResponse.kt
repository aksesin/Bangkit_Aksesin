package com.bangkit.aksesin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @field:SerializedName("predictions")
    val predictions: List<Prediction>
)

data class Prediction(
    @field:SerializedName("description")
    val desc: String? = "",

    @field:SerializedName("distance_meters")
    val distanceMeters: Int = 0,

    @field:SerializedName("place_id")
    val placeId: String? = "",

    @field:SerializedName("reference")
    val reference: String? = ""
)