package com.smtm.pickle.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.DailyLedgerUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import java.time.LocalDate

fun LazyListScope.dailyLedgerInfoSection(
    modifier: Modifier = Modifier,
    date: LocalDate,
    dailyLedger: DailyLedgerUi?,
) {
    item("selected_date") {
        SelectedDate(
            modifier = modifier,
            date = date
        )

        Spacer(modifier = Modifier.height(6.dp))
    }
    if (dailyLedger == null) {
        item("empty_notice") {
            EmptyNotice(modifier = modifier)
        }
    } else {
        item {
            SelectedDateAmount(
                modifier = modifier,
                totalIncome = dailyLedger.totalIncome ?: "0",
                totalExpense = dailyLedger.totalExpense ?: "0"
            )
        }

        items(
            items = dailyLedger.ledgers,
            key = { it.id }
        ) { item ->
            LedgerCard(
                modifier = modifier,
                title = stringResource(item.category.stringResId),
                description = stringResource(item.category.stringResId),
                amount = if (item.type == LedgerTypeUi.Income) {
                    "+${item.amount}"
                } else {
                    "-${item.amount}"
                },
                amountColor = if (item.type == LedgerTypeUi.Income) {
                    PickleTheme.colors.primary500
                } else {
                    PickleTheme.colors.error100
                },
                mainIconResId = item.category.activatedIconResId,
                subIconResId = item.paymentMethod.activatedIconResId,
                onClick = { }
            )
        }
    }
}

@Composable
private fun SelectedDate(
    modifier: Modifier = Modifier,
    date: LocalDate
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "${date.monthValue}월 ${date.dayOfMonth}일",
        textAlign = TextAlign.Start,
        style = PickleTheme.typography.body1Bold,
        color = PickleTheme.colors.gray700
    )
}

@Composable
fun EmptyNotice(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 74.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_ledger_empty),
            contentDescription = "empty_ledger",
            modifier = Modifier.size(120.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.ledger_empty_notice),
            modifier = Modifier.padding(vertical = 10.dp),
            style = PickleTheme.typography.body3Regular,
            color = PickleTheme.colors.gray600,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SelectedDateAmount(
    modifier: Modifier = Modifier,
    totalIncome: String,
    totalExpense: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "수입",
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.black,
        )

        Text(
            modifier = Modifier.width(110.dp),
            text = "+$totalIncome",
            textAlign = TextAlign.End,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.primary400
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "지출",
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.black,
        )

        Text(
            modifier = Modifier.width(110.dp),
            text = "-$totalExpense",
            textAlign = TextAlign.End,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.error100
        )
    }
}

@Composable
fun LedgerCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    amount: String,
    amountColor: Color,
    @DrawableRes mainIconResId: Int?,
    @DrawableRes subIconResId: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = PickleTheme.colors.base0,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = PickleTheme.colors.gray200),
                color = PickleTheme.colors.gray50
            ) {
                mainIconResId?.let { resId ->
                    Image(
                        painter = painterResource(resId),
                        contentDescription = "main_image",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = PickleTheme.typography.body4Medium,
                        color = PickleTheme.colors.gray800
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_ellipse_dot),
                        contentDescription = "ellipse_dot",
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Image(
                        painter = painterResource(subIconResId),
                        contentDescription = "sub_image",
                        modifier = Modifier.size(20.dp)
                    )
                }


                Text(
                    text = description,
                    style = PickleTheme.typography.caption2Regular,
                    color = PickleTheme.colors.gray200
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = amount,
                style = PickleTheme.typography.caption1Medium,
                color = amountColor
            )

            Spacer(modifier = Modifier.width(10.dp))

            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = "arrow_right",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(
    name = "LedgerCardPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCardPreview() {
    PickleTheme {
        LedgerCard(
            title = "여가/취미",
            description = "가계부 내역명 15자 입력",
            amount = "-000,000,000",
            amountColor = PickleTheme.colors.error100,
            mainIconResId = R.drawable.ic_ledger_category_leisure_hoby_activated,
            subIconResId = R.drawable.ic_ledger_payment_method_cash_activated,
            onClick = { }
        )
    }
}

@Preview(
    name = "SelectedDatePreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun SelectedDatePreview() {
    PickleTheme {
        SelectedDate(date = LocalDate.now())
    }
}

@Preview(
    name = "SelectedDateAmountPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun SelectedDateAmountPreview() {
    PickleTheme {
        SelectedDateAmount(
            totalIncome = "1,000,000",
            totalExpense = "500,000"
        )
    }
}

@Preview(
    name = "SelectedDateAmountPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun EmptyNoticePreview() {
    PickleTheme {
        EmptyNotice()
    }
}
