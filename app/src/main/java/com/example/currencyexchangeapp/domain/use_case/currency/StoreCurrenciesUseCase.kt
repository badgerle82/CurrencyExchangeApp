package com.example.currencyexchangeapp.domain.use_case.currency

import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.domain.use_case.base.UseCase

class StoreCurrenciesUseCase(
    private val repository: CurrencyExchangeRepository
) : UseCase<List<Currency>, Unit> {
    override suspend fun execute(param: List<Currency>): Unit =
        repository.storeCurrencies(param)
}