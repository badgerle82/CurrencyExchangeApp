package com.example.currencyexchangeapp.domain.use_case.currency

import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.domain.use_case.base.UseCaseBlocking
import kotlinx.coroutines.flow.Flow

class ObserveCurrenciesUseCase(
    private val repository: CurrencyExchangeRepository
): UseCaseBlocking<Nothing?, Flow<List<Currency>>> {
    override fun execute(param: Nothing?): Flow<List<Currency>> =
        repository.observeCurrencies()
}