package com.smtm.pickle.presentation.ledger.create.component.secondStep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.PaymentMethodUi

@Composable
fun LedgerCreateSecondContent(
    modifier: Modifier = Modifier,
    selectedPaymentMethod: PaymentMethodUi?,
    memo: String,
    onSelectedPaymentMethod: (PaymentMethodUi) -> Unit,
    onMemoChange: (String) -> Unit,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        LedgerPaymentMethodSelectors(
            modifier = Modifier.padding(top = 40.dp),
            selectedPaymentMethod = selectedPaymentMethod,
            onPaymentMethodClick = onSelectedPaymentMethod
        )

        Spacer(modifier = Modifier.height(20.dp))

        LedgerCreateMemo(
            memo = memo,
            onMemoChange = onMemoChange
        )

        Spacer(modifier = Modifier.height(40.dp))

        Spacer(modifier = Modifier.weight(1f))

        LedgerCreateSecondBottomButtons(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
            enabledSuccess = selectedPaymentMethod != null,
            onPreviousClick = onPreviousClick,
            onSuccessClick = onSuccessClick,
        )
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
            selectedPaymentMethod = null,
            memo = "",
            onSelectedPaymentMethod = {},
            onMemoChange = {},
            onPreviousClick = {},
            onSuccessClick = {},
        )
    }
}