package com.example.currencyexchangeapp.feature.validation

class ValidationAggregator(
    private val validators: List<ValidationUseCase>
): ValidationUseCase {

    override fun validate(): String {
        var result = ""
        validators.forEach {
            result = it.validate()
            if (result.isNotEmpty()) return@forEach
        }
        return result
    }
}