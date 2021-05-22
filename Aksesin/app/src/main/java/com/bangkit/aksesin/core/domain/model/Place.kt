package com.bangkit.aksesin.core.domain.model

import com.bangkit.aksesin.core.data.source.remote.response.Geometry

data class Place(
    val desc: String? = "",

    val distanceMeters: Int = 0,

    val placeId: String? = "",

    val reference: String? = "",

    val address: String? = "",

    val geometry: Geometry? = null,

    val name: String? = ""
)
