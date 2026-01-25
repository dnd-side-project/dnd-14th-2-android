package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.smtm.pickle.presentation.home.model.CalendarMode
import com.smtm.pickle.presentation.home.model.DailyLedgerModel
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
    dailyLedgerList: List<DailyLedgerModel>,
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        MonthHeader(
            yearMonth = currentMonth,
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
    val currentMonth = YearMonth.now()

    val sampleLedgerList = listOf(
        DailyLedgerModel(
            date = currentDate,
            dateText = "${currentDate.monthValue}월 ${currentDate.dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = "3,000,000",
            totalExpense = "10,000"
        ),
        DailyLedgerModel(
            date = currentDate.minusDays(1),
            dateText = "${currentDate.minusDays(1).monthValue}월 ${currentDate.minusDays(1).dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = null,
            totalExpense = "5,000"
        ),
        DailyLedgerModel(
            date = currentDate.plusDays(2),
            dateText = "${currentDate.plusDays(2).monthValue}월 ${currentDate.plusDays(2).dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = "100,000",
            totalExpense = null
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
        DailyLedgerModel(
            date = currentDate,
            dateText = "${currentDate.monthValue}월 ${currentDate.dayOfMonth}일",
            ledgers = emptyList(),
            totalIncome = "3,000,000",
            totalExpense = "10,000"
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
