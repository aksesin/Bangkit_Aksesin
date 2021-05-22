package com.bangkit.aksesin

import android.app.Application
import com.bangkit.aksesin.core.di.networkModule
import com.bangkit.aksesin.core.di.repositoryModule
import com.bangkit.aksesin.di.useCaseModule
import com.bangkit.aksesin.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}