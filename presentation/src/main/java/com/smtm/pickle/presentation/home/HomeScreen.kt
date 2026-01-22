package com.smtm.pickle.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
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