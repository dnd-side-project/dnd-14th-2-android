package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.DailyLedgerUi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun LedgerCalendar(
    modifier: Modifier = Modifier,
    dailyLedgerList: List<DailyLedgerUi>,
    calendarMode: CalendarMode,
    selectedDate: LocalDate,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onWeekChanged: (startDate: LocalDate, endDate: LocalDate) -> Unit
) {
    val currentDate = LocalDate.now()
    val currentMonth = YearMonth.now()
    val startMonth = currentMonth.minusMonths(12)
    val endMonth = currentMonth.plusMonths(12)

    val monthlyCalendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = DayOfWeek.SATURDAY,
    )

    val weeklyCalendarState = rememberWeekCalendarState(
        startDate = startMonth.atStartOfMonth(),
        endDate = endMonth.atEndOfMonth(),
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = DayOfWeek.SATURDAY,
    )

    LaunchedEffect(monthlyCalendarState) {
        snapshotFlow { monthlyCalendarState.firstVisibleMonth.yearMonth }
            .collect { visibleMonth -> onMonthChanged(visibleMonth) }
    }

    LaunchedEffect(weeklyCalendarState) {
        snapshotFlow { weeklyCalendarState.firstVisibleWeek }
            .collect { visibleWeek ->
                val weekStartDate = visibleWeek.days.first().date
                val weekEndDate = visibleWeek.days.last().date
                onWeekChanged(weekStartDate, weekEndDate)
            }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.base0)
            .padding(vertical = 16.dp)
    ) {
        MonthHeader(
            yearMonth = when (calendarMode) {
                CalendarMode.MONTHLY -> monthlyCalendarState.firstVisibleMonth.yearMonth
                CalendarMode.WEEKLY -> YearMonth.from(weeklyCalendarState.firstVisibleWeek.days.first().date)
            },
            calendarMode = calendarMode,
            onModeChange = onModeChange,
            onMonthArrowClick = onMonthArrowClick
        )
        Spacer(modifier = Modifier.height(20.dp))
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

    val sampleLedgerList = listOf(
        DailyLedgerUi(
            date = currentDate,
            dateText = "${currentDate.monthValue}월 ${currentDate.dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = "3,000,000",
            totalExpense = "10,000"
        ),
        DailyLedgerUi(
            date = currentDate.minusDays(1),
            dateText = "${currentDate.minusDays(1).monthValue}월 ${currentDate.minusDays(1).dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = null,
            totalExpense = "5,000"
        ),
        DailyLedgerUi(
            date = currentDate.plusDays(2),
            dateText = "${currentDate.plusDays(2).monthValue}월 ${currentDate.plusDays(2).dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = "100,000",
            totalExpense = null
        )
    )

    LedgerCalendar(
        dailyLedgerList = sampleLedgerList,
        calendarMode = CalendarMode.MONTHLY,
        selectedDate = currentDate,
        onModeChange = {},
        onMonthArrowClick = {},
        onDateClick = {},
        onMonthChanged = {},
        onWeekChanged = { _, _ -> },
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

    val sampleLedgerList = listOf(
        DailyLedgerUi(
            date = currentDate,
            dateText = "${currentDate.monthValue}월 ${currentDate.dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = "3,000,000",
            totalExpense = "10,000"
        )
    )

    LedgerCalendar(
        dailyLedgerList = sampleLedgerList,
        calendarMode = CalendarMode.WEEKLY,
        selectedDate = currentDate,
        onModeChange = {},
        onMonthArrowClick = {},
        onDateClick = {},
        onMonthChanged = {},
        onWeekChanged = { _, _ -> },
    )
}
