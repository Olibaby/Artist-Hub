package com.example.artisthub.application

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.artisthub.BuildConfig
import com.example.artisthub.core.di.module.*
import com.example.artisthub.core.di.networkServiceModule
import com.example.artisthub.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ArtistHubApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ArtistHubApplication)
            modules(listOf(networkServiceModule, repositoryModule, viewModelModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}