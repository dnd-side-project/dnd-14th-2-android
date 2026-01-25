package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.home.GetLedgersUseCase
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.DailyLedgerModel
import com.smtm.pickle.presentation.mapper.ledger.toDailyLedgerUiModel
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
    private val getLedgersUseCase: GetLedgersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadInitialDailyLedgers()
    }

    private fun loadInitialDailyLedgers() {
        viewModelScope.launch {
            val dailyLedgers = getLedgersUseCase().toDailyLedgerUiModel()
            _uiState.update { state ->
                state.copy(dailyLedgers = dailyLedgers)
            }
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

}

data class HomeUiState(
    val dailyLedgers: List<DailyLedgerModel> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarMode: CalendarMode = CalendarMode.MONTHLY,
)