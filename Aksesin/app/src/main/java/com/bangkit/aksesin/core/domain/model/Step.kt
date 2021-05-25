package com.bangkit.aksesin.core.domain.model

data class Step(
    val distance: Distance,
    val duration: Duration,
    val endLocation: Location,
    val startLocation: Location
)

data class DistanceStep(
    val text: String? = "",
    val value: Int = 0
)

data class DurationStep(
    val text: String? = "",
    val value: Int = 0
)

data class EndLocationStep(
    val lat: Double = 0.0,
    val lng: Double = 0.0
)

data class StartLocationStep(
    val lat: Double = 0.0,
    val lng: Double = 0.0
)