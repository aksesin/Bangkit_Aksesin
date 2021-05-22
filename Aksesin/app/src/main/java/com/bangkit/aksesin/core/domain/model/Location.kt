package com.bangkit.aksesin.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val lat: Double = 0.0,

    val lng: Double = 0.0
) : Parcelable