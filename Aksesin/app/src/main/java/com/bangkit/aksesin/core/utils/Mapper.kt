package com.bangkit.aksesin.core.utils

import com.bangkit.aksesin.core.data.source.remote.response.GeometryResponse
import com.bangkit.aksesin.core.data.source.remote.response.LocationResponse
import com.bangkit.aksesin.core.data.source.remote.response.Prediction
import com.bangkit.aksesin.core.data.source.remote.response.Result
import com.bangkit.aksesin.core.domain.model.Geometry
import com.bangkit.aksesin.core.domain.model.Location
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
    geometry = this.geometry.toGeometry()
)

fun GeometryResponse.toGeometry() = Geometry(
    this.location.toLocation()
)

fun LocationResponse.toLocation() = Location(
    this.lat, this.lng
)