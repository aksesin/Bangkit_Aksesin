package com.bangkit.aksesin.core.di

import com.bangkit.aksesin.core.data.MapRepository
import com.bangkit.aksesin.core.data.source.remote.PlacesRemoteDataSource
import com.bangkit.aksesin.core.data.source.remote.network.PlacesApiService
import com.bangkit.aksesin.core.domain.repository.IMapRepository
import com.bangkit.aksesin.core.utils.Constants.BASE_URL
import com.bangkit.aksesin.core.utils.Constants.REQUEST_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { PlacesRemoteDataSource(get()) }
    single<IMapRepository> { MapRepository(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        val apiInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        apiInstance.create(PlacesApiService::class.java)
    }
}