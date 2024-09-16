package com.example.currencyexchangeapp.data.interactor

import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.data.local.db.entity.Exchange
import com.example.currencyexchangeapp.domain.use_case.base.execute
import com.example.currencyexchangeapp.domain.use_case.currency.ObserveCurrenciesWithBalanceUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.CacheDailyRatesUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.ObserveCurrenciesUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.ObserveExchangeCountUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.StoreCurrenciesUseCase
import com.example.currencyexchangeapp.domain.use_case.currency.StoreExchangeUseCase
import kotlinx.coroutines.flow.Flow

class CurrencyExchangeInteractor(
    private val storeExchangeUseCase: StoreExchangeUseCase,
    private val observeExchangeCountUseCase: ObserveExchangeCountUseCase,
    private val storeCurrenciesUseCase: StoreCurrenciesUseCase,
    private val observeCurrenciesWithBalanceUseCase: ObserveCurrenciesWithBalanceUseCase,
    private val cacheDailyRatesUseCase: CacheDailyRatesUseCase,
    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase
) {
    suspend fun setupData() {
        cacheDailyRates()
    }

    fun observeCurrencies(): Flow<List<Currency>> = observeCurrenciesUseCase.execute()

    fun observeCurrenciesWithBalance(): Flow<List<Currency>> = observeCurrenciesWithBalanceUseCase.execute()

    fun observeExchangeCount(): Flow<Int> = observeExchangeCountUseCase.execute()

    suspend fun updateExchange(count: Int) {
        storeExchangeUseCase.execute(Exchange(id = 0, count = count))
    }

    suspend fun updateCurrencyBalances(currencies: List<Currency>) {
        storeCurrenciesUseCase.execute(currencies)
    }

    private suspend fun cacheDailyRates() {
        cacheDailyRatesUseCase.execute()
    }
}
