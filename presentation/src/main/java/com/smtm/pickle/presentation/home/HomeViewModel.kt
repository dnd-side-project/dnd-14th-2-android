package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.usecase.ledger.EnsureLedgersSyncedUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByMonthUseCase
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.DailyLedgerUi
import com.smtm.pickle.presentation.mapper.ledger.toDailyLedgerUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeLedgersByMonthUseCase: ObserveLedgersByMonthUseCase,
    private val ensureLedgersSyncedUseCase: EnsureLedgersSyncedUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val currentYearMonth = MutableStateFlow(YearMonth.now())

    init {
        syncInitialData()
        observeMonthLedgers()
    }

    private fun syncInitialData() {
        viewModelScope.launch {
            ensureLedgersSyncedUseCase()
        }
    }

    private fun ensureLedgersSynced(yearMonth: YearMonth) {
        viewModelScope.launch {
            ensureLedgersSyncedUseCase(
                baseMonth = yearMonth,
                monthsBack = 3,
                monthsForward = 3,
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeMonthLedgers() {
        currentYearMonth
            .flatMapLatest { yearMonth ->
                ensureLedgersSynced(yearMonth)
                observeLedgersByMonthUseCase(yearMonth)
            }
            .onEach { ledgers ->
                val dailyLedgers = ledgers.toDailyLedgerUiModel()
                val totalIncome = ledgers
                    .filter { it.type == LedgerType.INCOME }
                    .sumOf { it.amount.value }
                val totalExpense = ledgers
                    .filter { it.type == LedgerType.EXPENSE }
                    .sumOf { it.amount.value }

                _uiState.update { state ->
                    state.copy(
                        dailyLedgers = dailyLedgers,
                        monthlyTotalIncome = totalIncome,
                        monthlyTotalExpense = totalExpense,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        currentYearMonth.value = yearMonth
    }

    fun onWeekChanged(startDate: LocalDate, endDate: LocalDate) {
        // TODO 활성화된 날짜의 달 불어와야함.
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
}

data class HomeUiState(
    val dailyLedgers: List<DailyLedgerUi> = emptyList(),
    val monthlyTotalIncome: Long = 0L,
    val monthlyTotalExpense: Long = 0L,
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarMode: CalendarMode = CalendarMode.MONTHLY,
)