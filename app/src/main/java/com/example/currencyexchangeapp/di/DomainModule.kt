package com.example.currencyexchangeapp.di

import com.example.currencyexchangeapp.data.repository.CurrencyExchangeRepositoryImpl
import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.domain.use_case.currency.ObserveCurrenciesWithBalanceUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.CacheDailyRatesUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.ObserveCurrenciesUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.ObserveExchangeCountUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.StoreCurrenciesUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.StoreExchangeUseCase
import org.koin.dsl.module

val domainModule = module {

    single<CurrencyExchangeRepository> {
        CurrencyExchangeRepositoryImpl(get(), get())
    }
}

val useCaseModule = module {

    factory { StoreCurrenciesUseCase(get()) }
    factory { ObserveCurrenciesWithBalanceUseCase(get()) }
    factory { ObserveCurrenciesUseCase(get()) }
    factory { CacheDailyRatesUseCase(get()) }
    factory { StoreExchangeUseCase(get()) }
    factory { ObserveExchangeCountUseCase(get()) }
}