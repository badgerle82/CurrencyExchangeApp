package com.example.currencyexchangeapp.feature.exchange_fee

class FreeUpTo200EurosDecorator(
    private val wrappedDecorator: ExchangeFeeDecorator,
) : ExchangeFeeDecorator {

    override fun calculate(amount: Double): Double {
        return if (amount <= 200.0) {
            0.0 // Free if the amount is less than or equal to 200 Euros
        } else {
            wrappedDecorator.calculate(amount) // Delegate to the wrapped calculator
        }
    }
}