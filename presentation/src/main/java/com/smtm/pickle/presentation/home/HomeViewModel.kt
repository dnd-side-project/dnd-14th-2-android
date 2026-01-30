package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.summarize
import com.smtm.pickle.domain.usecase.ledger.EnsureLedgersSyncedUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByDayUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByMonthUseCase
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import com.smtm.pickle.presentation.home.model.LedgerUi
import com.smtm.pickle.presentation.mapper.ledger.toLedgerCalendarDays
import com.smtm.pickle.presentation.mapper.ledger.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeLedgersByMonthUseCase: ObserveLedgersByMonthUseCase,
    private val observeLedgersByDayUseCase: ObserveLedgersByDayUseCase,
    private val ensureLedgersSyncedUseCase: EnsureLedgersSyncedUseCase,
) : ViewModel() {

    private val selectedYearMonth = MutableStateFlow(YearMonth.now())
    private val selectedDate = MutableStateFlow(LocalDate.now())
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = combine(
        _uiState,
        selectedYearMonth,
        selectedDate
    ) { state, yearMonth, date ->
        state.copy(
            selectedYearMonth = yearMonth,
            selectedDate = date
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    init {
        syncInitialData()
        observeMonthLedgers()
        observeSelectedDateLedgers()
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
        selectedYearMonth
            .flatMapLatest { yearMonth ->
                ensureLedgersSynced(yearMonth)
                observeLedgersByMonthUseCase(yearMonth)
            }
            .onEach { ledgers ->
                val summary = ledgers.summarize()
                val ledgerCalendarDays = ledgers.toLedgerCalendarDays()
                _uiState.update { state ->
                    state.copy(
                        ledgerCalendarDays = ledgerCalendarDays,
                        monthlyTotalIncome = summary.totalIncome,
                        monthlyTotalExpense = summary.totalExpense,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedDateLedgers() {
        selectedDate
            .flatMapLatest { date ->
                observeLedgersByDayUseCase(date)
            }.onEach { ledgers ->
                val summary = ledgers.summarize()
                _uiState.update { state ->
                    state.copy(
                        dailyLedger = ledgers.map { it.toUiModel() },
                        dailyTotalIncome = summary.totalIncome,
                        dailyTotalExpense = summary.totalExpense
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        selectedYearMonth.value = yearMonth
    }

    fun selectDate(newSelectedDate: LocalDate) {
        selectedDate.value = newSelectedDate
    }

    fun changeCalendarMode(newMode: CalendarMode) {
        _uiState.update { state ->
            state.copy(calendarMode = newMode)
        }
    }
}

data class HomeUiState(
    val ledgerCalendarDays: List<LedgerCalendarDay> = emptyList(),
    val dailyLedger: List<LedgerUi> = emptyList(),
    val monthlyTotalIncome: Long = 0L,
    val monthlyTotalExpense: Long = 0L,
    val dailyTotalIncome: Long = 0L,
    val dailyTotalExpense: Long = 0L,
    val selectedYearMonth: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarMode: CalendarMode = CalendarMode.MONTHLY,
)