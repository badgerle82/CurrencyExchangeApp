package com.example.currencyexchangeapp.domain.use_case.currency

import com.example.currencyexchangeapp.data.local.db.entity.Exchange
import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.domain.use_case.base.UseCase

class StoreExchangeUseCase(
    private val repository: CurrencyExchangeRepository
) : UseCase<Exchange, Unit> {
    override suspend fun execute(param: Exchange) =
        repository.storeExchange(param)
}