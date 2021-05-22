package com.bangkit.aksesin.core.domain.usecase

import com.bangkit.aksesin.core.data.Resource
import com.bangkit.aksesin.core.domain.model.Place
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface IMapUseCase {

    fun searchPlaces(input: String, origin: LatLng): Flow<Resource<List<Place>>>

    fun getDetailPlace(placeId: String): Flow<Resource<Place>>
}