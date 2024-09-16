package com.example.currencyexchangeapp.feature.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyexchangeapp.R
import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.feature.utils.DIGITS_WITH_2_DECIMALS_REGEX
import com.example.currencyexchangeapp.ui.theme.Black
import com.example.currencyexchangeapp.ui.theme.Green

@Composable
fun CurrencyExchangeItem(
    initialSellItem: Currency,
    currencyItems: List<Currency>,
    currencyWithBalanceItems: List<Currency>,
    modifier: Modifier = Modifier,
    receiverInput: String,
    onInputChange: (String) -> Unit,
    onSellCurrencyChanged: (Currency) -> Unit,
    onReceiveCurrencyChanged: (Currency) -> Unit,
) {
    var inputText by remember { mutableStateOf("0") }

    Column(modifier = modifier.fillMaxWidth()) {
        CurrencyRow(
            iconId = R.drawable.up_red_arrow,
            label = "Sell",
            inputText = inputText,
            onInputChange = { newValue ->
                if(validateInputForMoneyFormat(newValue)) {
                    inputText = newValue
                    onInputChange.invoke(inputText)
                }
            },
            initialItem = initialSellItem,
            currencyNameItems = currencyWithBalanceItems,
            onCurrencySelected = onSellCurrencyChanged
        )

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(modifier = Modifier.padding(start = 40.dp))
        Spacer(modifier = Modifier.height(8.dp))
        CurrencyRow(
            iconId = R.drawable.down_green_arrow,
            label = "Receive",
            inputText = receiverInput,
            initialItem = currencyItems.first(),
            currencyNameItems = currencyItems,
            onCurrencySelected = onReceiveCurrencyChanged,
            isReadOnly = true
        )
    }
}

private fun validateInputForMoneyFormat(
    newValue: String
): Boolean {
    var result = false
    if (newValue.isEmpty() || newValue.matches(DIGITS_WITH_2_DECIMALS_REGEX)) {
        // Ensure the first character isn't 0 unless followed by a decimal
        if (!(newValue.startsWith("0") && newValue.length > 1 && newValue[1] != '.')) {
            result = true
        }
    }
    return result
}

@Composable
fun CurrencyRow(
    iconId: Int,
    label: String,
    inputText: String,
    initialItem: Currency,
    currencyNameItems: List<Currency>,
    isReadOnly: Boolean = false,
    onInputChange: (String) -> Unit = {},
    onCurrencySelected: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconId),
                contentDescription = "icon",
                modifier = Modifier.size(36.dp),
                tint = if (isReadOnly) Green else Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, color = Black)
        }
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (isReadOnly) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "+$inputText",
                    color = Green,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    fontSize = 16.sp
                )
            } else {
                TextField(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    value = inputText,
                    onValueChange = onInputChange,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        textAlign = TextAlign.End,
                        fontSize = 16.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            DropdownMenu(
                initialItem,
                currencyNameItems,
                modifier = Modifier.wrapContentSize(),
                onCurrencySelected
            )
        }
    }
}
