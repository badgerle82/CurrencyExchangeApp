package com.example.currencyexchangeapp.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {

    // Android
    androidContextModule()
}

fun androidContextModule() = module {
    single<Context> { androidContext() }
}