package com.example.currencyexchangeapp.domain.use_case.currency

import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.domain.use_case.base.UseCase

class CacheDailyRatesUseCase(
    private val repository: CurrencyExchangeRepository
): UseCase<Nothing?, Unit> {
    override suspend fun execute(param: Nothing?) {
        repository.getCurrencyExchangeRates()
    }
}