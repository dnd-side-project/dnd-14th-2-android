package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.domain.usecase.home.GetDailyLedgersUseCase
import com.smtm.pickle.presentation.model.calendar.CalendarMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyLedgersUseCase: GetDailyLedgersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadInitialDailyLedgers()
    }

    private fun loadInitialDailyLedgers() {
        viewModelScope.launch {
            val dailyLedgers = getDailyLedgersUseCase()
            val selectedDate = _uiState.value.selectedDate
            val selectedDailyLedger = dailyLedgers.firstOrNull { it.date == selectedDate }
            _uiState.update { state ->
                state.copy(
                    dailyLedgers = dailyLedgers,
                    selectedDailyLedger = selectedDailyLedger
                )
            }
        }
    }

    fun selectDate(newSelectedDate: LocalDate) {
        val newSelectedDailyLedger = _uiState.value.dailyLedgers.firstOrNull { it.date == newSelectedDate }
        _uiState.update { state ->
            state.copy(
                selectedDate = newSelectedDate,
                selectedDailyLedger = newSelectedDailyLedger
            )
        }
    }

    fun setCalendarMode(newMode: CalendarMode) {
        _uiState.update { state ->
            state.copy(calendarMode = newMode)
        }
    }
}

data class HomeUiState(
    val dailyLedgers: List<DailyLedger> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedDailyLedger: DailyLedger? = null,
    val calendarMode: CalendarMode = CalendarMode.MONTHLY,
)