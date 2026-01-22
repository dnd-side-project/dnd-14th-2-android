package com.smtm.pickle.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.domain.model.ledger.Expense
import com.smtm.pickle.domain.model.ledger.Income
import com.smtm.pickle.presentation.designsystem.components.calendar.LedgerCalendar
import com.smtm.pickle.presentation.navigation.navigator.HomeNavigator
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    navigator: HomeNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val startDate = remember { startMonth.atStartOfMonth() }
    val endDate = remember { endMonth.atEndOfMonth() }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            LedgerCalendar(
                currentDate = currentDate,
                currentMonth = currentMonth,
                startMonth = startMonth,
                endMonth = endMonth,
                startDate = startDate,
                endDate = endDate,
                firstDayOfWeek = firstDayOfWeek,
                daysOfWeek = daysOfWeek,
                dailyLedgerList = uiState.dailyLedgers,
                calendarMode = uiState.calendarMode,
                selectedDate = uiState.selectedDate,
                onModeChange = viewModel::setCalendarMode,
                onMonthArrowClick = { },
                onDateClick = viewModel::selectDate
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        detailLedgerDetailSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            date = uiState.selectedDate,
            dailyLedger = uiState.selectedDailyLedger
        )
    }
}

private fun LazyListScope.detailLedgerDetailSection(
    modifier: Modifier = Modifier,
    date: LocalDate,
    dailyLedger: DailyLedger?,
) {
    item {
        Text(
            text = "${date.monthValue}월 ${date.dayOfMonth}일",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    if (dailyLedger == null) {
        item {
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                text = "아직 지출 내역이 없어요",
                textAlign = TextAlign.Center
            )
        }
    } else {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "수입")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${dailyLedger.totalIncome}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "지출")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${dailyLedger.totalExpense}")
            }
        }

        items(
            items = dailyLedger.expenses,
            key = { it.id }
        ) { item ->
            ExpenseItem(
                expense = item,
                onClick = {}
            )
        }
        items(
            items = dailyLedger.incomes,
            key = { it.id }
        ) { item ->
            IncomeItem(
                income = item,
                onClick = {}
            )
        }
    }

}

@Composable
private fun DailyLedgerHeader(
    modifier: Modifier = Modifier,
    date: LocalDate,
    dailyLedger: DailyLedger?,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "${date.year}년 ${date.monthValue}월",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (dailyLedger == null) {
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                text = "아직 지출 내역이 없어요",
                textAlign = TextAlign.Center
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "수입")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${dailyLedger.totalIncome}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "지출")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${dailyLedger.totalExpense}")
            }
        }
    }
}

@Composable
private fun IncomeItem(
    modifier: Modifier = Modifier,
    income: Income,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = income.category)
                Text(text = income.title)
            }
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "-${income.amount}",
                textAlign = TextAlign.End
            )
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun ExpenseItem(
    modifier: Modifier = Modifier,
    expense: Expense,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = expense.category)
                Text(text = expense.title)
            }
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "+${expense.amount}",
                textAlign = TextAlign.End
            )
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                    contentDescription = null
                )
            }
        }
    }
}
