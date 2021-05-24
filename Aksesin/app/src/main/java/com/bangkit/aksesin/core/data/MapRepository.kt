package com.bangkit.aksesin.core.data

import android.util.Log
import com.bangkit.aksesin.core.data.source.remote.PlacesRemoteDataSource
import com.bangkit.aksesin.core.data.source.remote.network.ApiResponse
import com.bangkit.aksesin.core.domain.model.Place
import com.bangkit.aksesin.core.domain.repository.IMapRepository
import com.bangkit.aksesin.core.utils.toGeometry
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
                    result.map { place ->
                        remoteDataSource.getDetailPlace(place.placeId).collect { apiResponse ->
                            when (apiResponse) {
                                is ApiResponse.Success -> {
                                    val detail = apiResponse.data
                                    place.apply {
                                        address = detail.address
                                        name = detail.name
                                        geometry = detail.geometry.toGeometry()
                                    }
                                }
                                is ApiResponse.Empty -> {
                                    place.apply {
                                        address = ""
                                        name = ""
                                    }
                                }
                                is ApiResponse.Error -> {
                                    Log.d("searchPlaces", "Message: ${apiResponse.message}")
                                }
                            }
                        }
                    }
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