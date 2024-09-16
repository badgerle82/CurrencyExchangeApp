package com.example.currencyexchangeapp.di

import com.example.currencyexchangeapp.data.interactor.CurrencyExchangeInteractor
import com.example.currencyexchangeapp.data.local.db.AppDatabase
import com.example.currencyexchangeapp.data.remote.CurrencyExchangeApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    single<AppDatabase> {
        AppDatabase.getDatabase(androidContext())
    }

    factory { CurrencyExchangeInteractor(get(), get(), get(), get(), get(), get()) }
}

val networkModule = module {

    factory {
        val hostname = "https://developers.paysera.com/"

        Retrofit.Builder()
            .baseUrl(hostname)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(CurrencyExchangeApi::class.java)
    }

    // OkHttp
    factory() {
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .apply {
                addNetworkInterceptor(get<HttpLoggingInterceptor>())
            }
            .build()
    }

    // Gson
    factory {
        GsonBuilder()
            .setLenient()
            .create()
    }

    factory {
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}