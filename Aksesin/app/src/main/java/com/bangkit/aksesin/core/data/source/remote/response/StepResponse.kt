package com.bangkit.aksesin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class StepResponse(
    @field:SerializedName("distance")
    val distance: DistanceResponse,

    @field:SerializedName("duration")
    val duration: DurationResponse,

    @field:SerializedName("end_location")
    val endLocation: LocationResponse,

    @field:SerializedName("start_location")
    val startLocation: LocationResponse
)

data class DistanceStepResponse(
    @field:SerializedName("text")
    val text: String? = "",

    @field:SerializedName("value")
    val value: Int = 0
)

data class DurationStepResponse(
    @field:SerializedName("text")
    val text: String? = "",

    @field:SerializedName("value")
    val value: Int = 0
)

data class EndLocationStepResponse(
    @field:SerializedName("lat")
    val lat: Double = 0.0,

    @field:SerializedName("lng")
    val lng: Double = 0.0
)

data class StartLocationStepResponse(
    @field:SerializedName("lat")
    val lat: Double = 0.0,

    @field:SerializedName("lng")
    val lng: Double = 0.0
)