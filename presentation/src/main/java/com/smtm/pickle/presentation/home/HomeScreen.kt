package com.smtm.pickle.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.HomeProfile
import com.smtm.pickle.presentation.home.component.HomeTopBar
import com.smtm.pickle.presentation.home.component.LedgerCalendar
import com.smtm.pickle.presentation.home.component.dailyLedgerInfoSection
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import com.smtm.pickle.presentation.home.model.LedgerUi
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSelectedDateChange: (LocalDate) -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(uiState.selectedDate) {
        onSelectedDateChange(uiState.selectedDate)
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is HomeEffect.ShowSnackBar -> {
                        snackbarState.show(
                            PickleSnackbar.snackbarShort(
                                message = effect.msg,
                            )
                        )
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            monthlyTotalIncome = uiState.monthlyTotalIncome,
            monthlyTotalExpense = uiState.monthlyTotalExpense,
            ledgerCalendarDays = uiState.ledgerCalendarDays,
            dailyLedger = uiState.dailyLedger,
            dailyTotalIncome = uiState.dailyTotalIncome,
            dailyTotalExpense = uiState.dailyTotalExpense,
            calendarMode = uiState.calendarMode,
            selectedYearMonth = uiState.selectedYearMonth,
            selectedDate = uiState.selectedDate,
            onModeChange = viewModel::changeCalendarMode,
            onMonthArrowClick = { },
            onDateClick = viewModel::selectDate,
            onMonthChanged = viewModel::onMonthChanged,
        )

        SnackbarHost(snackbarState = snackbarState)
    }
}

@Composable
private fun HomeContent(
    monthlyTotalIncome: Long,
    monthlyTotalExpense: Long,
    ledgerCalendarDays: List<LedgerCalendarDay>,
    dailyLedger: List<LedgerUi>,
    dailyTotalIncome: Long,
    dailyTotalExpense: Long,
    calendarMode: CalendarMode,
    selectedYearMonth: YearMonth,
    selectedDate: LocalDate,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
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
                    ledgerCalendarDays = ledgerCalendarDays,
                    calendarMode = calendarMode,
                    selectedYearMonth = selectedYearMonth,
                    selectedDate = selectedDate,
                    onModeChange = onModeChange,
                    onMonthArrowClick = onMonthArrowClick,
                    onDateClick = onDateClick,
                    onMonthChanged = onMonthChanged,
                )
            }

            dailyLedgerInfoSection(
                date = selectedDate,
                ledgers = dailyLedger,
                totalIncome = dailyTotalIncome,
                totalExpense = dailyTotalExpense,
            )
        }
    }
}

