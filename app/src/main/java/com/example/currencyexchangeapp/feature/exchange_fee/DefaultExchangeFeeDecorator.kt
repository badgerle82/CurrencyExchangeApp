package com.example.currencyexchangeapp.feature.exchange_fee

import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.feature.utils.DEFAULT_EXCHANGE_FEE_EUR_AMOUNT
import com.example.currencyexchangeapp.feature.utils.roundToTwoDecimalPlaces

class DefaultExchangeFeeDecorator(
    private val sellCurrency: Currency
): ExchangeFeeDecorator {

    override fun calculate(amount: Double): Double =
        (DEFAULT_EXCHANGE_FEE_EUR_AMOUNT * sellCurrency.rate).roundToTwoDecimalPlaces()
}