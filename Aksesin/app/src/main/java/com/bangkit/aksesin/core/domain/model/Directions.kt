package com.bangkit.aksesin.core.domain.model

data class Route(
    val legs: List<Leg>,
    val overviewPolyline: Points
)
