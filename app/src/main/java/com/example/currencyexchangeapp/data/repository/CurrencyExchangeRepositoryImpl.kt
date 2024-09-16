package com.example.currencyexchangeapp.data.repository

import com.example.currencyexchangeapp.data.local.db.AppDatabase
import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.data.local.db.entity.Exchange
import com.example.currencyexchangeapp.data.remote.CurrencyExchangeApi
import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.feature.utils.BASE_CURRENCY_NAME
import com.example.currencyexchangeapp.feature.utils.INITIAL_BASE_CURRENCY_BALANCE
import kotlinx.coroutines.flow.Flow

class CurrencyExchangeRepositoryImpl(
    private val db: AppDatabase,
    private val restApi: CurrencyExchangeApi
): CurrencyExchangeRepository {

    private var ratesTimestamp: String = ""

    private val currencyDao = db.currencyDao()
    private val exchangeDao = db.exchangeDao()

    override suspend fun getCurrencyExchangeRates() {
        val ratesResponse = restApi.getCurrenciesExchangeRates()
        if (ratesTimestamp != ratesResponse.date) {
            ratesTimestamp = ratesResponse.date

            val rates: MutableMap<String, Double> = ratesResponse.rates.toMutableMap()

            val currencies = currencyDao.getCurrencies()
            if (currencies.size == rates.size) {
                val updatedCurrencies: List<Currency> = currencies.map { Currency(it.name, it.balance, rates[it.name] ?: 0.0) }
                currencyDao.updateCurrencies(updatedCurrencies)
            } else {
                val updatedCurrencies: List<Currency> = rates.map { if (it.key == BASE_CURRENCY_NAME) Currency(it.key, INITIAL_BASE_CURRENCY_BALANCE, it.value) else Currency(it.key, 0.0, it.value)  }
                currencyDao.insertCurrencies(updatedCurrencies)
            }
        }
    }

    override suspend fun storeCurrencies(currencies: List<Currency>) {
        currencyDao.updateCurrencies(currencies)
    }

    override fun getCurrenciesWithBalance(): Flow<List<Currency>> = currencyDao.observeCurrenciesWithBalance()

    override fun observeCurrencies(): Flow<List<Currency>> = currencyDao.observeCurrencies()

    override suspend fun storeExchange(exchange: Exchange): Unit = exchangeDao.insertExchange(exchange)

    override fun observeExchangesCount(): Flow<Int> = exchangeDao.observeExchangesCount()
}