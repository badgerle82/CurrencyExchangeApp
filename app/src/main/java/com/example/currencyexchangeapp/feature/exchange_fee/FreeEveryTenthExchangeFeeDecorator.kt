package com.example.currencyexchangeapp.feature.exchange_fee

class FreeEveryTenthExchangeFeeDecorator(
    private val wrappedDecorator: ExchangeFeeDecorator,
    private val exchangeCount: Int
): ExchangeFeeDecorator {

    override fun calculate(amount: Double): Double {
        return if (exchangeCount % 3 == 0) {
            0.0 // Free for every tenth exchange
        } else {
            wrappedDecorator.calculate(amount) // Delegate to the wrapped decorator
        }
    }
}