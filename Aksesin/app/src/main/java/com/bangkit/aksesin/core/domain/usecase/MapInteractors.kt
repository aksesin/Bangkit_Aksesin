package com.bangkit.aksesin.core.domain.usecase

import com.bangkit.aksesin.core.data.Resource
import com.bangkit.aksesin.core.domain.model.Place
import com.bangkit.aksesin.core.domain.model.Route
import com.bangkit.aksesin.core.domain.repository.IMapRepository
import kotlinx.coroutines.flow.Flow

class MapInteractors(private val mapRepository: IMapRepository) : IMapUseCase {

    override fun searchPlaces(input: String, origin: String): Flow<Resource<List<Place>>> {
        return mapRepository.searchPlaces(input, origin)
    }

    override fun getDetailPlace(placeId: String): Flow<Resource<Place>> {
        return mapRepository.getDetailPLace(placeId)
    }

    override fun getDirections(
        origin: String,
        destination: String
    ): Flow<Resource<List<Route>>> {
        return mapRepository.getDirections(origin, destination)
    }
}