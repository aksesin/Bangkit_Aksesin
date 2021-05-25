package com.bangkit.aksesin.ui.util

import com.google.android.gms.maps.model.LatLng
import java.text.DecimalFormat

fun Int.toKmString(): String {
    val toDouble = this.toDouble()
    val decimalFormat = DecimalFormat("#.#")
    val toKm: Double = toDouble / 1000
    return "${decimalFormat.format(toKm)} Km"
}

fun LatLng.toLatLngString(): String {
    val lat = this.latitude
    val lng = this.longitude
    return "$lat,$lng"
}

fun String.toLatLng(): LatLng {
    val latLng = this.split(",")
    return LatLng(latLng[0].toDouble(), latLng[1].toDouble())
}