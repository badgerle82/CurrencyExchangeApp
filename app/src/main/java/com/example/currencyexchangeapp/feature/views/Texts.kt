package com.example.currencyexchangeapp.feature.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchangeapp.ui.theme.Black
import com.example.currencyexchangeapp.ui.theme.Grey

@Composable
fun TitleMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Grey,
    textAlign : TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        style = MaterialTheme.typography.titleMedium,
        color = color
    )
}

@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = Black,
    textAlign: TextAlign? = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        style = style.copy(
            platformStyle =
            PlatformTextStyle(
                includeFontPadding = false
            )
        ),
        color = color,
        textAlign = textAlign
    )
}