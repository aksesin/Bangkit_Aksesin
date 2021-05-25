package com.bangkit.aksesin.core.domain.model

data class Leg(
    val distance: Distance,
    val duration: Duration,
    val endAddress: String? = "",
    val endLocation: EndLocation,
    val startAddress: String? = "",
    val startLocation: StartLocation
    //val steps: List<Step>
)

data class Points(
    val point: String? = ""
)

data class Distance(
    val text: String? = "",
    val value: Int = 0
)

data class Duration(
    val text: String? = "",
    val value: Int = 0
)

data class EndLocation(
    val lat: Double = 0.0,
    val lng: Double = 0.0
)

data class StartLocation(
    val lat: Double = 0.0,
    val lng: Double = 0.0
)