package com.example.currencyexchangeapp.di

import com.example.currencyexchangeapp.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {

    viewModel { HomeViewModel(get()) }
}