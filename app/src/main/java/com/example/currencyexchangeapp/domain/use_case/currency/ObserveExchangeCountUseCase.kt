package com.example.currencyexchangeapp.domain.use_case.currency

import com.example.currencyexchangeapp.domain.repository.CurrencyExchangeRepository
import com.example.currencyexchangeapp.domain.use_case.base.UseCaseBlocking
import kotlinx.coroutines.flow.Flow

class ObserveExchangeCountUseCase(
    private val repository: CurrencyExchangeRepository
): UseCaseBlocking<Nothing?, Flow<Int>> {
    override fun execute(param: Nothing?): Flow<Int> =
        repository.observeExchangesCount()
}