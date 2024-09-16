package com.example.currencyexchangeapp.feature.utils

const val BASE_CURRENCY_NAME = "EUR"
const val INITIAL_BASE_CURRENCY_BALANCE = 1000.0
val ONLY_DIGITS_WITHOUT_SPACE_REGEX = Regex("[^\\d+\$]")
val DIGITS_WITH_DOT_REGEX = Regex("[^0-9.]")
val DIGITS_WITH_2_DECIMALS_REGEX = Regex("^\\d*\\.?\\d{0,2}$") // Only allows digits and 2 decimals

const val DEFAULT_EXCHANGE_FEE_EUR_AMOUNT = 0.70