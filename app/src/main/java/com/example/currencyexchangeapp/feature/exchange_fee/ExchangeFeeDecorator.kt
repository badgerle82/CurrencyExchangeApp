package com.example.currencyexchangeapp.feature.exchange_fee

interface ExchangeFeeDecorator {
    fun calculate(amount: Double): Double
}