package com.bangkit.aksesin.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.aksesin.core.domain.usecase.IMapUseCase
import kotlinx.coroutines.ObsoleteCoroutinesApi

@Suppress("DEPRECATION")
@ObsoleteCoroutinesApi
class SearchViewModel(private val useCase: IMapUseCase) : ViewModel() {

    fun searchPlace(input: String, origin: String) =
        useCase.searchPlaces(input, origin).asLiveData(viewModelScope.coroutineContext)

}