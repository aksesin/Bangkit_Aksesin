package com.bangkit.aksesin.core.utils

import com.bangkit.aksesin.core.data.source.remote.response.*
import com.bangkit.aksesin.core.domain.model.*

fun List<Prediction>.toListPlaces(): List<Place> {
    val places = ArrayList<Place>()
    this.map {
        val place = Place(
            locationName = it.desc,
            distanceMeters = it.distanceMeters,
            placeId = it.placeId,
            reference = it.reference
        )
        places.add(place)
    }
    return places
}

fun List<RouteResponse>.toListRoutes(): List<Route> {
    val routes = ArrayList<Route>()
    this.map {
        val route = Route(
            legs = it.legs.toListLegs(),
            overviewPolyline = it.overviewPolyline.toPoints()
        )
        routes.add(route)
    }
    return routes
}

fun List<LegResponse>.toListLegs(): List<Leg> {
    val legs = ArrayList<Leg>()
    this.map {
        val leg = Leg(
            distance = it.distance.toDistance(),
            duration = it.duration.toDuration(),
            endAddress = it.endAddress,
            endLocation = it.endLocation.toEndLocation(),
            startAddress = it.toString(),
            startLocation = it.startLocation.toStartLocation()
        )
        legs.add(leg)
    }
    return legs
}

fun PointsResponse.toPoints() = Points(
    this.point
)

fun List<StepResponse>.toListSteps(): List<Step> {
    val steps = ArrayList<Step>()
    this.map {
        val step = Step(
            distance = it.distance.toDistance(),
            duration = it.duration.toDuration(),
            endLocation = it.endLocation.toLocation(),
            startLocation = it.startLocation.toLocation()
        )
        steps.add(step)
    }
    return steps
}

fun Result.toPlace() = Place(
    name = this.name,
    placeId = this.placeId,
    address = this.address,
    geometry = this.geometry.toGeometry()
)

fun DurationResponse.toDuration() = Duration(
    text = this.text,
    value = this.value
)

fun DistanceResponse.toDistance() = Distance(
    text = this.text,
    value = this.value
)

fun GeometryResponse.toGeometry() = Geometry(
    this.location.toLocation()
)

fun LocationResponse.toLocation() = Location(
    this.lat, this.lng
)

fun EndLocationResponse.toEndLocation() = EndLocation(
    this.lat, this.lng
)

fun StartLocationResponse.toStartLocation() = StartLocation(
    this.lat, this.lng
)