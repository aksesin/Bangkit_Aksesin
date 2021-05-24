package com.bangkit.aksesin.core.data.source.remote

import com.bangkit.aksesin.core.data.source.remote.network.ApiResponse
import com.bangkit.aksesin.core.data.source.remote.network.PlacesApiService
import com.bangkit.aksesin.core.data.source.remote.response.Prediction
import com.bangkit.aksesin.core.data.source.remote.response.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlacesRemoteDataSource(private val apiService: PlacesApiService) {

    suspend fun searchPlaces(input: String, origin: String): Flow<ApiResponse<List<Prediction>>> {
        return flow {
            val response = apiService.searchPlaces(input = input, origin = origin)
            val data = response.predictions.subList(0, 5)
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailPlace(placeId: String?): Flow<ApiResponse<Result>> {
        return flow {
            val response = apiService.getDetailPlace(placeId = placeId)
            val data = response.result
            if (data.placeId != null) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

}