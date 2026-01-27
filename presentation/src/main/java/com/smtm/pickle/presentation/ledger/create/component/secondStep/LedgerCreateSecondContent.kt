package com.smtm.pickle.presentation.ledger.create.component.secondStep

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun LedgerCreateSecondContent(
    modifier: Modifier = Modifier,
    selectedPaymentMethod: PaymentMethodUi?,
    memo: String,
    onSelectedPaymentMethod: (PaymentMethodUi) -> Unit,
    onMemoChange: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val memoScrollExtraPx = with(LocalDensity.current) { 40.dp.toPx() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        LedgerPaymentMethodSelectors(
            selectedPaymentMethod = selectedPaymentMethod,
            onPaymentMethodClick = onSelectedPaymentMethod
        )

        Spacer(modifier = Modifier.height(20.dp))

        LedgerCreateMemo(
            modifier = Modifier.padding(bottom = 40.dp),
            inputModifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { state ->
                    if (state.isFocused) {
                        scope.launch {
                            awaitFrame()
                            bringIntoViewRequester.bringIntoView()
                            scrollState.animateScrollBy(memoScrollExtraPx)
                        }
                    }
                },
            memo = memo,
            onMemoChange = onMemoChange
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
        )
    }
}