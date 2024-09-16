package com.example.currencyexchangeapp.domain.repository

import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.data.local.db.entity.Exchange
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRepository {

    suspend fun getCurrencyExchangeRates()

    suspend fun storeCurrencies(currencies: List<Currency>)

    fun getCurrenciesWithBalance(): Flow<List<Currency>>

    fun observeCurrencies(): Flow<List<Currency>>

    suspend fun storeExchange(exchange: Exchange)

    fun observeExchangesCount(): Flow<Int>
}