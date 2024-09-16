package com.example.currencyexchangeapp.feature.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.currencyexchangeapp.data.local.db.entity.Currency

@Composable
fun BalanceList(
    modifier: Modifier = Modifier,
    items: List<Currency>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items.size) {
            items[it].let { item ->
                Row {
                    Text(
                        text = item.balance.toString(),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.name.uppercase(),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}