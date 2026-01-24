package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.domain.model.ledger.Expense
import com.smtm.pickle.domain.model.ledger.Income
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import java.time.DayOfWeek
import java.time.LocalDate

private val IncomeColor = Color(0xFF2196F3)
private val ExpenseColor = Color(0xFFF44336)
private const val IncomePrefix = "+"
private const val ExpensePrefix = "-"

@Composable
fun MonthlyDayCell(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    dailyLedger: DailyLedger?,
    onClick: (CalendarDay) -> Unit,
) {
    val isActiveDay = day.position == DayPosition.MonthDate
    DayCell(
        modifier = modifier,
        date = day.date,
        isActiveDay = isActiveDay,
        isSelected = isSelected,
        dailyLedger = dailyLedger,
        onClick = { onClick(day) }
    )
}

@Composable
fun WeeklyDayCell(
    modifier: Modifier = Modifier,
    day: WeekDay,
    isSelected: Boolean,
    dailyLedger: DailyLedger?,
    onclick: (WeekDay) -> Unit,
) {
    val isActiveDay = day.position == WeekDayPosition.RangeDate
    DayCell(
        modifier = modifier,
        date = day.date,
        isActiveDay = isActiveDay,
        isSelected = isSelected,
        dailyLedger = dailyLedger,
        onClick = { onclick(day) }
    )
}


@Composable
private fun DayCell(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isActiveDay: Boolean,
    isSelected: Boolean,
    dailyLedger: DailyLedger?,
    onClick: () -> Unit,
) {
    val dateTextColor = when {
        !isActiveDay -> Color.LightGray
        isSelected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(1.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable(enabled = isActiveDay, onClick = onClick),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = dateTextColor,
                fontSize = 20.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )

            if (dailyLedger != null) {
                dailyLedger.totalExpense.takeIf { it > 0 }?.let { totalExpense ->
                    Spacer(modifier = Modifier.height(2.dp))
                    ExpenseItem(amount = totalExpense)
                }
                dailyLedger.totalIncome.takeIf { it > 0 }?.let { totalIncome ->
                    Spacer(modifier = Modifier.height(2.dp))
                    IncomeItem(amount = totalIncome)
                }
            }
        }
    }
}

@Composable
private fun ExpenseItem(
    modifier: Modifier = Modifier,
    amount: Long,
) {
    Text(
        modifier = modifier,
        text = "$ExpensePrefix${amount.toMoneyFormat()}",
        color = ExpenseColor,
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun IncomeItem(
    modifier: Modifier = Modifier,
    amount: Long,
) {
    Text(
        modifier = modifier,
        text = "$IncomePrefix${amount.toMoneyFormat()}",
        color = IncomeColor,
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true, name = "Default")
@Composable
private fun DayCellPreview() {
    MaterialTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = true,
            isSelected = false,
            dailyLedger = null,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Selected")
@Composable
private fun DayCellSelectedPreview() {
    MaterialTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = true,
            isSelected = true,
            dailyLedger = null,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With Finance")
@Composable
private fun DayCellWithFinancePreview() {
    MaterialTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = true,
            isSelected = false,
            dailyLedger = DailyLedger(
                date = LocalDate.of(2024, 1, 15),
                incomes = listOf(Income(id = 1, title = "월급", amount = 3000000, category = "급여")),
                expenses = listOf(Expense(id = 1, title = "점심", amount = 12000, category = "식비"))
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Inactive")
@Composable
private fun DayCellInactivePreview() {
    MaterialTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = false,
            isSelected = false,
            dailyLedger = null,
            onClick = {}
        )
    }
}