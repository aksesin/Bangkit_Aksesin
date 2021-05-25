package com.bangkit.aksesin

import android.app.Application
import com.bangkit.aksesin.core.di.networkModule
import com.bangkit.aksesin.core.di.repositoryModule
import com.bangkit.aksesin.di.useCaseModule
import com.bangkit.aksesin.di.viewModelModule
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ObsoleteCoroutinesApi
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
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