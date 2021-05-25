package com.bangkit.aksesin.core.domain.repository

import com.bangkit.aksesin.core.data.Resource
import com.bangkit.aksesin.core.domain.model.Place
import com.bangkit.aksesin.core.domain.model.Route
import kotlinx.coroutines.flow.Flow

interface IMapRepository {

    fun searchPlaces(input: String, origin: String): Flow<Resource<List<Place>>>

    fun getDetailPLace(placeId: String): Flow<Resource<Place>>

    fun getDirections(origin: String, destination: String): Flow<Resource<List<Route>>>
}