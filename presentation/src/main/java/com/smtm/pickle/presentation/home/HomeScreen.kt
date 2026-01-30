package com.smtm.pickle.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
    onSelectedDateChange: (LocalDate) -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.selectedDate) {
        onSelectedDateChange(uiState.selectedDate)
    }

    HomeContent(
        onStatisticsClick = {},
        onAlarmClick = {},
        monthlyTotalIncome = uiState.monthlyTotalIncome,
        monthlyTotalExpense = uiState.monthlyTotalExpense,
        dailyLedgerList = uiState.dailyLedgers,
        calendarMode = uiState.calendarMode,
        selectedDate = uiState.selectedDate,
        onModeChange = viewModel::changeCalendarMode,
        onMonthArrowClick = { },
        onDateClick = viewModel::selectDate,
        onMonthChanged = viewModel::onMonthChanged,
        onWeekChanged = viewModel::onWeekChanged
    )
}

@Composable
private fun HomeContent(
    onStatisticsClick: () -> Unit,
    onAlarmClick: () -> Unit,
    monthlyTotalIncome: Long,
    monthlyTotalExpense: Long,
    dailyLedgerList: List<DailyLedgerUi>,
    calendarMode: CalendarMode,
    selectedDate: LocalDate,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onWeekChanged: (startDate: LocalDate, endDate: LocalDate) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PickleTheme.colors.background50
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item("top_bar") {
                HomeTopBar(
                    modifier = Modifier.background(PickleTheme.colors.base0),
                    onStatisticsClick = {},
                    onAlarmClick = {}
                )
            }
            item("profile") {
                HomeProfile(
                    modifier = Modifier.background(PickleTheme.colors.base0),
                    badge = "뱃지명",
                    nickname = "나의닉네임",
                    income = monthlyTotalIncome,
                    expense = monthlyTotalExpense,
                )
            }
            item("ledger_calendar") {
                LedgerCalendar(
                    modifier = Modifier
                        .background(PickleTheme.colors.base0)
                        .padding(horizontal = 12.dp),
                    dailyLedgerList = dailyLedgerList,
                    calendarMode = calendarMode,
                    selectedDate = selectedDate,
                    onModeChange = onModeChange,
                    onMonthArrowClick = onMonthArrowClick,
                    onDateClick = onDateClick,
                    onMonthChanged = onMonthChanged,
                    onWeekChanged = onWeekChanged
                )
            }

            dailyLedgerInfoSection(
                date = selectedDate,
                dailyLedger = dailyLedgerList.firstOrNull { it.date == selectedDate }
            )
        }
    }
}

