package com.bangkit.aksesin.core.data

import com.bangkit.aksesin.core.data.source.remote.PlacesRemoteDataSource
import com.bangkit.aksesin.core.data.source.remote.network.ApiResponse
import com.bangkit.aksesin.core.domain.model.Place
import com.bangkit.aksesin.core.domain.repository.IMapRepository
import com.bangkit.aksesin.core.utils.toListPlaces
import com.bangkit.aksesin.core.utils.toPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@Suppress("UNCHECKED_CAST")
class MapRepository(private val remoteDataSource: PlacesRemoteDataSource) : IMapRepository {

    override fun searchPlaces(input: String, origin: String): Flow<Resource<List<Place>>> {
        return flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.searchPlaces(input, origin).first()) {
                is ApiResponse.Success -> {
                    val result = response.data.toListPlaces()
                    emit(Resource.Success(result))
                }
                is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                is ApiResponse.Empty -> emit(Resource.Success<List<Place>>(emptyList()))
            }
        } as Flow<Resource<List<Place>>>
    }

    override fun getDetailPLace(placeId: String): Flow<Resource<Place>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getDetailPlace(placeId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toPlace()))
                    is ApiResponse.Empty -> emit(Resource.Success<Place>(null))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<Place>>
    }
}