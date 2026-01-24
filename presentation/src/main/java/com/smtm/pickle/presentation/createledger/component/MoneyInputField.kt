package com.smtm.pickle.presentation.createledger.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.smtm.pickle.presentation.common.utils.ThousandsSeparatorTransformation

@Composable
fun MoneyInputField(
    modifier: Modifier = Modifier,
    emptyDigit: String = "0",
    rawDigits: String,
    onRawDigitsChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = rawDigits,
        onValueChange = { input ->
            val digitsOnly = input.filter(Char::isDigit)
            val normalized = digitsOnly.trimStart('0').ifEmpty { emptyDigit }

            onRawDigitsChange(normalized)
        },
        leadingIcon = { Text("₩") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        visualTransformation = ThousandsSeparatorTransformation(emptyText = emptyDigit),
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            textAlign = TextAlign.End
        ),
        singleLine = true,
    )
}

