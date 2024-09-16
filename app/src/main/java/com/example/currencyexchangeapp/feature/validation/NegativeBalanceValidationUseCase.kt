package com.example.currencyexchangeapp.feature.validation


class NegativeBalanceValidationUseCase(
    private val balance: Double,
    private val amountToSell: Double
): ValidationUseCase {

    override fun validate(): String {
        return if (balance < amountToSell) {
            "You balance is lees than exchange amount"
        } else {
            ""
        }
    }
}