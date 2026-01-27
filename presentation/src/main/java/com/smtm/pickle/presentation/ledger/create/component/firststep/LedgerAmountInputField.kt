package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtm.pickle.presentation.common.utils.ThousandsSeparatorTransformation
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

private const val EmptyDigit = "0"

@Composable
fun LedgerAmountInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = { new ->
            val digitsOnly = new.filter(Char::isDigit)
            val normalized = digitsOnly.trimStart('0').ifEmpty { EmptyDigit }
            onValueChange(normalized)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = PickleTheme.typography.head4SemiBold.copy(
            fontSize = 40.sp,
            color = PickleTheme.colors.gray800,
            textAlign = TextAlign.End
        ),
        visualTransformation = ThousandsSeparatorTransformation(emptyText = EmptyDigit),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 20.dp),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "₩",
                    style = PickleTheme.typography.head4SemiBold.copy(fontSize = 48.sp),
                    color = PickleTheme.colors.gray700
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    innerTextField()
                }
            }
        }
    )
}

@Preview(
    name = "LedgerAmountInputFieldPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerAmountInputFieldPreview() {
    PickleTheme {
        LedgerAmountInputField(
            value = "3000000",
            onValueChange = {}
        )
    }
}

@Preview(
    name = "LedgerAmountInputFieldPreview - Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerAmountInputFieldEmptyPreview() {
    PickleTheme {
        LedgerAmountInputField(
            value = "",
            onValueChange = {}
        )
    }
}