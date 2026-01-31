package com.smtm.pickle.presentation.ledger.create.component.secondStep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateHeaderText

@Composable
fun LedgerPaymentMethodSelectors(
    modifier: Modifier = Modifier,
    selectedPaymentMethod: PaymentMethodUi? = null,
    onPaymentMethodClick: (PaymentMethodUi) -> Unit
) {
    Column(modifier = modifier) {
        LedgerCreateHeaderText(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            text = stringResource(R.string.ledger_create_payment_method_header),
            highlightText = stringResource(R.string.ledger_create_payment_method_header_highlight)
        )

        PaymentMethodGrid(
            modifier = Modifier.padding(horizontal = 16.dp),
            selectedPaymentMethod = selectedPaymentMethod,
            onPaymentMethodClick = onPaymentMethodClick,
        )
    }
}

@Composable
private fun PaymentMethodGrid(
    modifier: Modifier = Modifier,
    selectedPaymentMethod: PaymentMethodUi? = null,
    onPaymentMethodClick: (PaymentMethodUi) -> Unit,
) {
    val paymentMethods = listOf(
        PaymentMethodUi.CreditCard, PaymentMethodUi.Cash,
        PaymentMethodUi.DebitCard, PaymentMethodUi.BankTransfer
    )
    val rows = paymentMethods.chunked(2)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { paymentMethod ->
                    PaymentMethodChip(
                        modifier = Modifier.weight(1f),
                        paymentMethod = paymentMethod,
                        isSelected = paymentMethod == selectedPaymentMethod,
                        onClick = { onPaymentMethodClick(paymentMethod) }
                    )
                }

            }
        }
    }
}

@Composable
private fun PaymentMethodChip(
    modifier: Modifier = Modifier,
    paymentMethod: PaymentMethodUi,
    isSelected: Boolean,
    onClick: (PaymentMethodUi) -> Unit,
) {
    val backgroundColor = if (isSelected) PickleTheme.colors.gray700 else PickleTheme.colors.gray50
    val textColor = if (isSelected) PickleTheme.colors.base0 else PickleTheme.colors.gray700
    val imageResId = if (isSelected) paymentMethod.activatedIconResId else paymentMethod.inactivatedIconResId

    Surface(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(Dimensions.radius),
        color = backgroundColor,
        onClick = { onClick(paymentMethod) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(imageResId),
                contentDescription = "payment_method_icon",
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(paymentMethod.stringResId),
                style = PickleTheme.typography.body1Bold,
                color = textColor
            )
        }
    }
}

@Preview(
    name = "LedgerPaymentMethodSelectorsPreview - Non Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerPaymentMethodSelectorsNonSelectedPreview() {
    PickleTheme {
        LedgerPaymentMethodSelectors(
            selectedPaymentMethod = null,
            onPaymentMethodClick = {}
        )
    }
}

@Preview(
    name = "LedgerPaymentMethodSelectorsPreview - Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerPaymentMethodSelectorsSelectedPreview() {
    PickleTheme {
        LedgerPaymentMethodSelectors(
            selectedPaymentMethod = PaymentMethodUi.DebitCard,
            onPaymentMethodClick = {}
        )
    }
}