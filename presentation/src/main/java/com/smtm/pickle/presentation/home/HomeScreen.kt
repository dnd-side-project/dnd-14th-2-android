package com.smtm.pickle.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kizitonwose.calendar.core.atStartOfMonth
import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.presentation.home.component.LedgerCalendar
import com.smtm.pickle.presentation.home.model.CalendarMode
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLedgerCreate: () -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()


    HomeContent(
        dailyLedgerList = uiState.dailyLedgers,
        calendarMode = uiState.calendarMode,
        selectedDate = uiState.selectedDate,
        onModeChange = viewModel::changeCalendarMode,
        onMonthArrowClick = { },
        onDateClick = viewModel::selectDate
    )
}

@Composable
private fun HomeContent(
    dailyLedgerList: List<DailyLedger>,
    calendarMode: CalendarMode,
    selectedDate: LocalDate,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val startDate = remember { startMonth.atStartOfMonth() }
    val endDate = remember { endMonth.atEndOfMonth() }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LedgerCalendar(
                currentDate = currentDate,
                currentMonth = currentMonth,
                startMonth = startMonth,
                endMonth = endMonth,
                startDate = startDate,
                endDate = endDate,
                dailyLedgerList = dailyLedgerList,
                calendarMode = calendarMode,
                selectedDate = selectedDate,
                onModeChange = onModeChange,
                onMonthArrowClick = onMonthArrowClick,
                onDateClick = onDateClick,
            )
        }
    }
}
