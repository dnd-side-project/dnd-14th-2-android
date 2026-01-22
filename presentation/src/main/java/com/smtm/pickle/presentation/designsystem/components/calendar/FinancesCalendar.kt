package com.smtm.pickle.presentation.designsystem.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.presentation.model.calendar.CalendarMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun LedgerCalendar(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    currentMonth: YearMonth,
    startMonth: YearMonth,
    endMonth: YearMonth,
    startDate: LocalDate,
    endDate: LocalDate,
    firstDayOfWeek: DayOfWeek,
    daysOfWeek: List<DayOfWeek>,
    dailyLedgerList: List<DailyLedger>,
    calendarMode: CalendarMode,
    selectedDate: LocalDate,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit
) {
    // 월간 캘린더 상태
    val monthCalendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    // 주간 캘린더 상태
    val weekCalendarState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    val visibleYearMonth = when (calendarMode) {
        CalendarMode.MONTHLY -> monthCalendarState.firstVisibleMonth.yearMonth
        CalendarMode.WEEKLY -> YearMonth.from(weekCalendarState.firstVisibleWeek.days.first().date)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        MonthHeader(
            yearMonth = visibleYearMonth,
            calendarMode = calendarMode,
            onModeChange = onModeChange,
            onMonthArrowClick = onMonthArrowClick
        )
        WeekDaysHeader(daysOfWeek = daysOfWeek)
        Spacer(modifier = Modifier.height(8.dp))
        when (calendarMode) {
            CalendarMode.MONTHLY -> {
                HorizontalCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    state = monthCalendarState,
                    contentHeightMode = ContentHeightMode.Wrap,
                    dayContent = { day ->
                        MonthlyDayCell(
                            day = day,
                            isSelected = selectedDate == day.date,
                            dailyLedger = dailyLedgerList.firstOrNull { it.date == day.date },
                            onClick = { clickedDay ->
                                onDateClick(clickedDay.date)
                            },
                        )
                    }
                )
            }

            CalendarMode.WEEKLY -> {
                WeekCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    state = weekCalendarState,
                    dayContent = { day ->
                        WeeklyDayCell(
                            day = day,
                            isSelected = selectedDate == day.date,
                            dailyLedger = dailyLedgerList.firstOrNull { it.date == day.date },
                            onClick = { clickedDay ->
                                onDateClick(clickedDay.date)
                            },
                        )
                    }
                )
            }
        }
    }
}