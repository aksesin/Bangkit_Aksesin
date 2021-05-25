package com.bangkit.aksesin.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.aksesin.core.domain.usecase.IMapUseCase

class HomeViewModel(private val useCase: IMapUseCase) : ViewModel() {

    fun getDirections(origin: String, destination: String) =
        useCase.getDirections(origin, destination).asLiveData(viewModelScope.coroutineContext)
}