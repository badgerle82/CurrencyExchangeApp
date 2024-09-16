package com.example.currencyexchangeapp.feature.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.feature.exchange_fee.DefaultExchangeFeeDecorator
import com.example.currencyexchangeapp.feature.exchange_fee.FreeEveryTenthExchangeFeeDecorator
import com.example.currencyexchangeapp.feature.exchange_fee.FreeUpTo200EurosDecorator
import com.example.currencyexchangeapp.feature.home.HomeViewModel
import com.example.currencyexchangeapp.feature.utils.BASE_CURRENCY_NAME
import com.example.currencyexchangeapp.feature.utils.roundToTwoDecimalPlaces
import com.example.currencyexchangeapp.feature.validation.NegativeBalanceValidationUseCase
import com.example.currencyexchangeapp.feature.validation.ValidationAggregator
import com.example.currencyexchangeapp.ui.theme.Grey
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainViewScreen(
    modifier: Modifier
) {
    val homeViewModel: HomeViewModel = koinViewModel()

    val currencies by homeViewModel.observeCurrenciesFlow.collectAsState()

    val currenciesWithBalance by homeViewModel.observeCurrenciesWithBalanceFlow.collectAsState()

    val exchangeCount by homeViewModel.observeExchangeCountFlow.collectAsState()

    val infoDialogMessage = homeViewModel.infoDialogMessage.collectAsState().value

    val sellCurrency = remember { mutableStateOf<Currency?>(null) }

    val receiveCurrency = remember { mutableStateOf<Currency?>(null) }

    val sellInput = remember { mutableStateOf<String>("0") }

    val receiveInput = remember { mutableStateOf<String>("0") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "MY BALANCE",
            color = Grey,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        BalanceList(
            items = currenciesWithBalance
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "CURRENCY EXCHANGE",
            color = Grey,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (currencies.isNotEmpty() && currenciesWithBalance.isNotEmpty()) {
            if (sellCurrency.value == null) sellCurrency.value =
                currenciesWithBalance.firstOrNull { it.name == BASE_CURRENCY_NAME }
                    ?: currenciesWithBalance.first()
            if (receiveCurrency.value == null) receiveCurrency.value = currencies.first()
            CurrencyExchangeItem(
                currenciesWithBalance.firstOrNull { it.name == BASE_CURRENCY_NAME }
                    ?: currenciesWithBalance.first(),
                currencies,
                currenciesWithBalance,
                modifier = Modifier.fillMaxWidth(),
                receiveInput.value,
                onInputChange = { input ->
                    sellInput.value = input
                    if (sellCurrency.value != null && receiveCurrency.value != null) {
                        receiveInput.value =
                            calculateReceiveOutcome(sellInput, sellCurrency, receiveCurrency)
                    }
                },
                onSellCurrencyChanged = { currency ->
                    sellCurrency.value = currency
                    receiveInput.value =
                        calculateReceiveOutcome(sellInput, sellCurrency, receiveCurrency)
                },
                onReceiveCurrencyChanged = { currency ->
                    receiveCurrency.value = currency
                    receiveInput.value =
                        calculateReceiveOutcome(sellInput, sellCurrency, receiveCurrency)
                },
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
        SubmitButton(
            onClick = {
                val sellAmount = sellInput.value.toDoubleOrNull() ?: 0.0
                val localSellCurrency = sellCurrency.value
                if (sellAmount <= 0.0) return@SubmitButton
                localSellCurrency?.let {
                    val feeCalculator = FreeUpTo200EurosDecorator(
                        FreeEveryTenthExchangeFeeDecorator(
                            DefaultExchangeFeeDecorator(
                                localSellCurrency
                            ), exchangeCount + 1
                        )
                    )
                    val exchangeFee = feeCalculator.calculate(sellAmount)
                    val validationRules = ValidationAggregator(
                        listOf(
                            NegativeBalanceValidationUseCase(
                                localSellCurrency.balance,
                                (sellAmount + exchangeFee).roundToTwoDecimalPlaces()
                            )
                        )
                    )
                    val validationResult = validationRules.validate()
                    if (validationResult.isNotEmpty()) {
                        homeViewModel.onNewInfoDialog(validationResult)
                    } else {
                        val localReceiveCurrency = receiveCurrency.value
                        localReceiveCurrency?.let {
                            val updatedSellCurrency =
                                localSellCurrency.copy(balance = (localSellCurrency.balance - (sellAmount + exchangeFee).roundToTwoDecimalPlaces()).roundToTwoDecimalPlaces())
                            val receiveAmount = receiveInput.value.toDoubleOrNull() ?: 0.0
                            val updatedReceiveCurrency =
                                localReceiveCurrency.copy(balance = (it.balance + receiveAmount).roundToTwoDecimalPlaces())
                            homeViewModel.updateCurrencyBalances(
                                listOf(
                                    updatedSellCurrency, updatedReceiveCurrency
                                )
                            )
                            homeViewModel.updateExchange(exchangeCount + 1)
                            sellCurrency.value = updatedSellCurrency
                            receiveCurrency.value = updatedReceiveCurrency
                            homeViewModel.onNewInfoDialog("You have converted $sellAmount ${localSellCurrency.name} to $receiveAmount ${localReceiveCurrency.name}. Commission Fee: $exchangeFee ${localSellCurrency.name}")
                        }
                    }
                }
            }
        )
    }
    infoDialogMessage?.let { message ->
        InfoDialog(
            onDismiss = {
                homeViewModel.onInfoDialogShown()
            },
            text = message
        )
    }
}

private fun calculateReceiveOutcome(
    sellInput: MutableState<String>,
    sellCurrency: MutableState<Currency?>,
    receiveCurrency: MutableState<Currency?>
): String {
    val result = ((sellInput.value.toDoubleOrNull() ?: 0.0).div(sellCurrency.value?.rate ?: 0.0)
        .times(receiveCurrency.value?.rate ?: 0.0)).roundToTwoDecimalPlaces().toString()
    return result
}
