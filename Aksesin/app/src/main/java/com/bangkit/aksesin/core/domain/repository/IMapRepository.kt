package com.bangkit.aksesin.core.domain.repository

import com.bangkit.aksesin.core.data.Resource
import com.bangkit.aksesin.core.domain.model.Place
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface IMapRepository {

    fun searchPlaces(input: String, origin: LatLng): Flow<Resource<List<Place>>>

    fun getDetailPLace(placeId: String): Flow<Resource<Place>>
}