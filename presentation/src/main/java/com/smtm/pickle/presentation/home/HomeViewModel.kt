package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.usecase.home.GetInitialLedgersUseCase
import com.smtm.pickle.domain.usecase.home.GetLedgersByMonthUseCase
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.DailyLedgerUi
import com.smtm.pickle.presentation.mapper.ledger.toDailyLedgerUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getInitialLedgersUseCase: GetInitialLedgersUseCase,
    private val getLedgersByMonthUseCase: GetLedgersByMonthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val loadingMonths = mutableSetOf<YearMonth>()
    private val ledgerCache = mutableMapOf<YearMonth, List<LedgerEntry>>()
    private var previousMonth: YearMonth? = null
    private var currentActiveMonth: YearMonth? = null

    fun loadInitialLedgers() {
        viewModelScope.launch {
            val entries = getInitialLedgersUseCase()

            entries.groupBy { YearMonth.from(it.occurredOn) }
                .forEach { (month, monthEntries) ->
                    ledgerCache[month] = monthEntries
                }

            updatedDailyLedgersFromCache()
        }
    }

    private fun updatedDailyLedgersFromCache() {
        val allEntries = ledgerCache.values.flatten()
        val dailyLedgers = allEntries.toDailyLedgerUiModel()

        _uiState.update { state ->
            state.copy(dailyLedgers = dailyLedgers)
        }
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        val direction = detectDirection(yearMonth)
        previousMonth = yearMonth

        updateActiveMonth(yearMonth)

        val monthsToLoad = when (direction) {
            Direction.PAST -> listOf(
                yearMonth,
                yearMonth.minusMonths(1),
                yearMonth.minusMonths(2),
            )

            Direction.FUTURE -> listOf(
                yearMonth,
                yearMonth.plusMonths(1),
                yearMonth.plusMonths(2),
            )

            Direction.NONE -> listOf(yearMonth)
        }

        loadMonthsLedgers(monthsToLoad)
    }

    fun onWeekChanged(startDate: LocalDate, endDate: LocalDate) {
        val weekMonth = YearMonth.from(startDate)
        if (weekMonth != currentActiveMonth) {
            updateActiveMonth(weekMonth)
        }

        val startMonth = YearMonth.from(startDate)
        val endMonth = YearMonth.from(endDate)

        val monthsToLoad = mutableListOf<YearMonth>()
        var current = startMonth
        while (current <= endMonth) {
            monthsToLoad.add(current)
            current = current.plusMonths(1)
        }

        val direction = detectDirection(startMonth)
        when (direction) {
            Direction.PAST -> monthsToLoad.add(startMonth.minusMonths(1))
            Direction.FUTURE -> monthsToLoad.add(startMonth.plusMonths(1))
            Direction.NONE -> {}
        }

        previousMonth = startMonth
        loadMonthsLedgers(monthsToLoad)
    }

    private fun loadMonthsLedgers(months: List<YearMonth>) {
        val monthsToLoad = months.filter { month ->
            month !in loadingMonths && month !in ledgerCache
        }

        if (monthsToLoad.isEmpty()) return

        loadingMonths.addAll(monthsToLoad)

        viewModelScope.launch {
            monthsToLoad.map { month ->
                async {
                    month to getLedgersByMonthUseCase(month)
                }
            }.awaitAll()
                .forEach { (month, entries) ->
                    ledgerCache[month] = entries
                }

            updatedDailyLedgersFromCache()
            loadingMonths.removeAll(monthsToLoad.toSet())
        }
    }

    private fun detectDirection(current: YearMonth): Direction {
        val prev = previousMonth ?: return Direction.NONE
        return when {
            current < prev -> Direction.PAST
            current > prev -> Direction.FUTURE
            else -> Direction.NONE
        }
    }

    fun selectDate(newSelectedDate: LocalDate) {
        _uiState.update { state ->
            state.copy(selectedDate = newSelectedDate)
        }
    }

    fun changeCalendarMode(newMode: CalendarMode) {
        _uiState.update { state ->
            state.copy(calendarMode = newMode)
        }
    }

    private fun updateActiveMonth(yearMonth: YearMonth) {
        currentActiveMonth = yearMonth

        val monthlyLedgers = ledgerCache[yearMonth] ?: emptyList()
        updateMonthlyAmount(monthlyLedgers)
    }

    private fun updateMonthlyAmount(monthlyLedgers: List<LedgerEntry>) {
        val totalIncome = monthlyLedgers
            .filter { it.type == LedgerType.INCOME }
            .sumOf { it.amount.value }

        val totalExpense = monthlyLedgers
            .filter { it.type == LedgerType.EXPENSE }
            .sumOf { it.amount.value }

        _uiState.update { state ->
            state.copy(
                monthlyTotalIncome = totalIncome,
                monthlyTotalExpense = totalExpense
            )
        }
    }

    private enum class Direction { PAST, NONE, FUTURE }
}

data class HomeUiState(
    val dailyLedgers: List<DailyLedgerUi> = emptyList(),
    val monthlyTotalIncome: Long = 0L,
    val monthlyTotalExpense: Long = 0L,
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarMode: CalendarMode = CalendarMode.MONTHLY,
)