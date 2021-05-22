package com.bangkit.aksesin.di

import com.bangkit.aksesin.core.domain.usecase.IMapUseCase
import com.bangkit.aksesin.core.domain.usecase.MapInteractors
import com.bangkit.aksesin.ui.home.HomeViewModel
import com.bangkit.aksesin.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMapUseCase> { MapInteractors(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { HomeViewModel() }
}