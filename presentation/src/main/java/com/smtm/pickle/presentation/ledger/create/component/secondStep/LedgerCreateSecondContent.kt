package com.smtm.pickle.presentation.ledger.create.component.secondStep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.PaymentMethodUi

@Composable
fun LedgerCreateSecondContent(
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {

    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethodUi?>(null) }
    var memo by remember { mutableStateOf("") }
    val enabledSuccess = selectedPaymentMethod != null

    Column(modifier = modifier) {
        LedgerPaymentMethodSelectors(
            selectedPaymentMethod = selectedPaymentMethod,
            onPaymentMethodClick = { it -> selectedPaymentMethod = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        LedgerCreateMemo(
            memo = memo,
            onMemoChange = { memo = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PickleButton(
                modifier = Modifier.width(96.dp),
                text = "이전",
                color = PickleTheme.colors.gray100,
                textColor = PickleTheme.colors.gray600,
                onClick = onPreviousClick,
            )

            PickleButton(
                modifier = Modifier.weight(1f),
                text = "입력 완료",
                color = if (enabledSuccess) PickleTheme.colors.primary400 else PickleTheme.colors.gray100,
                textColor = if (enabledSuccess) PickleTheme.colors.base0 else PickleTheme.colors.gray600,
                onClick = onSuccessClick,
                enabled = enabledSuccess,
            )
        }
    }
}

@Preview(
    name = "LedgerCreateSecondContentPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondContentPreview() {
    PickleTheme {
        LedgerCreateSecondContent(
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}