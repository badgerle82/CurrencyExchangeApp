package com.example.currencyexchangeapp.data.remote

import com.example.currencyexchangeapp.domain.model.CurrenciesExchangeRates
import retrofit2.http.GET

interface CurrencyExchangeApi {

    @GET("tasks/api/currency-exchange-rates")
    suspend fun getCurrenciesExchangeRates(): CurrenciesExchangeRates
}