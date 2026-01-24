package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.domain.model.ledger.Expense
import com.smtm.pickle.domain.model.ledger.Income
import com.smtm.pickle.presentation.home.model.CalendarMode
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
    dailyLedgerList: List<DailyLedger>,
    calendarMode: CalendarMode,
    selectedDate: LocalDate,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
) {
    val monthlyCalendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = DayOfWeek.SATURDAY,
    )

    val weeklyCalendarState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = DayOfWeek.SATURDAY,
    )

    Column(modifier = modifier.fillMaxWidth()) {
        MonthHeader(
            yearMonth = currentMonth,
            calendarMode = calendarMode,
            onModeChange = onModeChange,
            onMonthArrowClick = onMonthArrowClick
        )
        WeekDaysHeader()
        when (calendarMode) {
            CalendarMode.MONTHLY -> {
                HorizontalCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    state = monthlyCalendarState,
                    contentHeightMode = ContentHeightMode.Wrap,
                    dayContent = { day ->
                        MonthlyDayCell(
                            day = day,
                            isSelected = selectedDate == day.date,
                            dailyLedger = dailyLedgerList.firstOrNull { it.date == day.date },
                            onClick = { clickedDay ->
                                onDateClick(clickedDay.date)
                            }
                        )
                    }
                )
            }

            CalendarMode.WEEKLY -> {
                WeekCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    state = weeklyCalendarState,
                    dayContent = { day ->
                        WeeklyDayCell(
                            day = day,
                            isSelected = selectedDate == day.date,
                            dailyLedger = dailyLedgerList.firstOrNull { it.date == day.date },
                            onclick = { clickedDay ->
                                onDateClick(clickedDay.date)
                            }
                        )
                    }
                )
            }
        }
    }
}

@Preview(
    name = "LedgerCalendar - Monthly",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCalendarMonthlyPreview() {
    val currentDate = LocalDate.now()
    val currentMonth = YearMonth.now()

    val sampleLedgerList = listOf(
        DailyLedger(
            date = currentDate,
            incomes = listOf(Income(1L, "월급", 3000000L, "급여")),
            expenses = listOf(Expense(1L, "점심", 10000L, "식비"))
        ),
        DailyLedger(
            date = currentDate.minusDays(1),
            expenses = listOf(Expense(2L, "커피", 5000L, "카페"))
        ),
        DailyLedger(
            date = currentDate.plusDays(2),
            incomes = listOf(Income(2L, "용돈", 100000L, "기타"))
        )
    )

    LedgerCalendar(
        currentDate = currentDate,
        currentMonth = currentMonth,
        startMonth = currentMonth.minusMonths(12),
        endMonth = currentMonth.plusMonths(12),
        startDate = currentDate.minusMonths(12),
        endDate = currentDate.plusMonths(12),
        dailyLedgerList = sampleLedgerList,
        calendarMode = CalendarMode.MONTHLY,
        selectedDate = currentDate,
        onModeChange = {},
        onMonthArrowClick = {},
        onDateClick = {}
    )
}

@Preview(
    name = "LedgerCalendar - Weekly",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCalendarWeeklyPreview() {
    val currentDate = LocalDate.now()
    val currentMonth = YearMonth.now()

    val sampleLedgerList = listOf(
        DailyLedger(
            date = currentDate,
            incomes = listOf(Income(1L, "월급", 3000000L, "급여")),
            expenses = listOf(Expense(1L, "점심", 10000L, "식비"))
        )
    )

    LedgerCalendar(
        currentDate = currentDate,
        currentMonth = currentMonth,
        startMonth = currentMonth.minusMonths(12),
        endMonth = currentMonth.plusMonths(12),
        startDate = currentDate.minusMonths(12),
        endDate = currentDate.plusMonths(12),
        dailyLedgerList = sampleLedgerList,
        calendarMode = CalendarMode.WEEKLY,
        selectedDate = currentDate,
        onModeChange = {},
        onMonthArrowClick = {},
        onDateClick = {}
    )
}
