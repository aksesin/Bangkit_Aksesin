package com.bangkit.aksesin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LegResponse(
    @field:SerializedName("distance")
    val distance: DistanceResponse,

    @field:SerializedName("duration")
    val duration: DurationResponse,

    @field:SerializedName("end_address")
    val endAddress: String,

    @field:SerializedName("end_location")
    val endLocation: EndLocationResponse,

    @field:SerializedName("start_address")
    val startAddress: String? = "",

    @field:SerializedName("start_location")
    val startLocation: StartLocationResponse

    /* @field:SerializedName("steps")
     val steps: List<StepResponse>,*/
)

data class PointsResponse(
    @field:SerializedName("points")
    val point: String? = ""
)

data class DistanceResponse(
    @field:SerializedName("text")
    val text: String? = "",

    @field:SerializedName("value")
    val value: Int = 0
)

data class DurationResponse(
    @field:SerializedName("text")
    val text: String? = "",

    @field:SerializedName("value")
    val value: Int = 0
)

data class EndLocationResponse(
    @field:SerializedName("lat")
    val lat: Double = 0.0,

    @field:SerializedName("lng")
    val lng: Double = 0.0
)

data class StartLocationResponse(
    @field:SerializedName("lat")
    val lat: Double = 0.0,

    @field:SerializedName("lng")
    val lng: Double = 0.0
)