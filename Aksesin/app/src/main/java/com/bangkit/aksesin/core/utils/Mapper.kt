package com.bangkit.aksesin.core.utils

import com.bangkit.aksesin.core.data.source.remote.response.Prediction
import com.bangkit.aksesin.core.data.source.remote.response.Result
import com.bangkit.aksesin.core.domain.model.Place

fun List<Prediction>.toListPlaces(): List<Place> {
    val places = ArrayList<Place>()
    this.map {
        val place = Place(
            desc = it.desc,
            distanceMeters = it.distanceMeters,
            placeId = it.placeId,
            reference = it.reference
        )
        places.add(place)
    }
    return places
}

fun Result.toPlace() = Place(
    name = this.name,
    placeId = this.placeId,
    address = this.address,
    geometry = this.geometry
)