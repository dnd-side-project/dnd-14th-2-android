package com.smtm.pickle.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kizitonwose.calendar.core.atStartOfMonth
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.HomeProfile
import com.smtm.pickle.presentation.home.component.HomeTopBar
import com.smtm.pickle.presentation.home.component.LedgerCalendar
import com.smtm.pickle.presentation.home.component.dailyLedgerInfoSection
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.DailyLedgerUi
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
        onStatisticsClick = {},
        onAlarmClick = {},
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
    onStatisticsClick: () -> Unit,
    onAlarmClick: () -> Unit,
    dailyLedgerList: List<DailyLedgerUi>,
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
        color = PickleTheme.colors.base0
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item("top_bar") {
                HomeTopBar(
                    onStatisticsClick = {},
                    onAlarmClick = {}
                )
            }
            item("profile") {
                HomeProfile(
                    badge = "뱃지명",
                    nickname = "나의닉네임",
                    income = 1000000,
                    expense = 500000,
                )
            }
            item("ledger_calendar") {
                LedgerCalendar(
                    modifier = Modifier.padding(horizontal = 12.dp),
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

            dailyLedgerInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                date = selectedDate,
                dailyLedger = dailyLedgerList.firstOrNull { it.date == selectedDate }
            )
        }
    }
}

