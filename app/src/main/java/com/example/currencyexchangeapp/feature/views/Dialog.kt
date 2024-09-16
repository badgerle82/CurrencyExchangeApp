package com.example.currencyexchangeapp.feature.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.currencyexchangeapp.R
import com.example.currencyexchangeapp.ui.theme.Grey300


@Composable
fun InfoDialog(
    title: String = stringResource(id = R.string.info),
    text: String?,
    onDismiss: () -> Unit
) {
    SimpleDialog(
        title = title,
        text = text,
        onPositive = onDismiss,
        onPositiveText = stringResource(id = android.R.string.ok),
        onDismiss = onDismiss,
        onNegative = null,
        onNegativeText = null
    )
}

@Composable
private fun SimpleDialog(
    title: String,
    text: String? = null,
    onPositive: () -> Unit,
    onPositiveText: String,
    onDismiss: () -> Unit,
    onNegative: (() -> Unit)?,
    onNegativeText: String?,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Grey300, shape = RoundedCornerShape(16.dp))
        ) {

            val modifier = Modifier.padding(horizontal = 16.dp)

            TitleMediumText(text = title, modifier = modifier.padding(vertical = 16.dp))
            text?.let {
                BodyText(text = text, modifier = modifier)
                Spacer(modifier = Modifier.height(16.dp))
            }
            PrimaryButton(
                modifier = modifier,
                onClick = {
                    onPositive()
                },
                title = onPositiveText
            )
            Spacer(modifier = Modifier.height(8.dp))
            onNegative?.let { safeAction ->
                SecondaryButton(
                    modifier = modifier.padding(bottom = 16.dp),
                    onClick = {
                        safeAction()
                    },
                    title = onNegativeText ?: stringResource(id = R.string.back)
                )
            }

        }
    }
}


