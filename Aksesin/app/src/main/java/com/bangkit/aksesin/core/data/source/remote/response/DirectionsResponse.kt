package com.bangkit.aksesin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    @field:SerializedName("routes")
    val routes: List<RouteResponse>
)

data class RouteResponse(
    @field:SerializedName("legs")
    val legs: List<LegResponse>,

    @field:SerializedName("overview_polyline")
    val overviewPolyline: PointsResponse
)
