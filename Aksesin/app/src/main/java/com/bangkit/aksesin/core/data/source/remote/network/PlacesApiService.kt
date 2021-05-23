package com.bangkit.aksesin.core.data.source.remote.network

import com.bangkit.aksesin.BuildConfig.MAPS_API_KEY
import com.bangkit.aksesin.core.data.source.remote.response.PlaceDetailResponse
import com.bangkit.aksesin.core.data.source.remote.response.PlacesResponse
import com.bangkit.aksesin.core.utils.Constants.FIELDS
import com.bangkit.aksesin.core.utils.Constants.INDONESIA
import com.bangkit.aksesin.core.utils.Constants.OUTPUT_AS_JSON
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesApiService {

    @GET("autocomplete/{output}")
    suspend fun searchPlaces(
        @Path("output") output: String = OUTPUT_AS_JSON,
        @Query("input") input: String,
        @Query("origin") origin: String,
        @Query("key") apiKey: String = MAPS_API_KEY,
        @Query("language") language: String = INDONESIA,
        @Query("components") components: String = "country:$INDONESIA"
    ): PlacesResponse

    @GET("details/{output}")
    suspend fun getDetailPlace(
        @Path("output") output: String = OUTPUT_AS_JSON,
        @Query("key") key: String = MAPS_API_KEY,
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = FIELDS
    ): PlaceDetailResponse
}