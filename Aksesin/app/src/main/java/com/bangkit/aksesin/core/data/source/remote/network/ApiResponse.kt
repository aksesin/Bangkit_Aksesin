package com.bangkit.aksesin.core.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error<out T>(val message: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}