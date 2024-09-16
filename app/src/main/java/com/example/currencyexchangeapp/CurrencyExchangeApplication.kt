package com.example.currencyexchangeapp

import android.app.Application
import com.example.currencyexchangeapp.di.androidModule
import com.example.currencyexchangeapp.di.dataModule
import com.example.currencyexchangeapp.di.domainModule
import com.example.currencyexchangeapp.di.networkModule
import com.example.currencyexchangeapp.di.featureModule
import com.example.currencyexchangeapp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class CurrencyExchangeApplication: Application() {

    var koinApplication: KoinApplication? = null
        private set

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        koinApplication = startKoin {
            androidContext(this@CurrencyExchangeApplication)
            modules(
                androidModule,
                featureModule,
                dataModule,
                domainModule,
                useCaseModule,
                networkModule
            )
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}