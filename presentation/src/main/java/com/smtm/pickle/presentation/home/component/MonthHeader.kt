package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.home.model.CalendarMode
import java.time.YearMonth

@Composable
fun MonthHeader(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    calendarMode: CalendarMode,
    onModeChange: (CalendarMode) -> Unit,
    onMonthArrowClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            IconButton(onClick = onMonthArrowClick) {
                Box(
                    modifier
                        .size(20.dp)
                        .background(Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        CalendarModeToggle(
            calendarMode = calendarMode,
            onModeChange = onModeChange
        )

    }
}

@Composable
fun CalendarModeToggle(
    modifier: Modifier = Modifier,
    calendarMode: CalendarMode,
    onModeChange: (CalendarMode) -> Unit,
) {
    val containerColor = when (calendarMode) {
        CalendarMode.MONTHLY -> Color.White
        CalendarMode.WEEKLY -> Color(0xFF3F4246)
    }
    val contentColor = when (calendarMode) {
        CalendarMode.MONTHLY -> Color(0xFF3F4246)
        CalendarMode.WEEKLY -> Color.White
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp),
        color = containerColor,
        contentColor = contentColor,
        border = if (calendarMode == CalendarMode.MONTHLY) BorderStroke(1.dp, Color.Black) else null,
        onClick = {
            val newMode = if (calendarMode == CalendarMode.MONTHLY) {
                CalendarMode.WEEKLY
            } else {
                CalendarMode.MONTHLY
            }
            onModeChange(newMode)
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(contentColor)
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "주간"
            )
        }
    }
}

@Preview(
    name = "MonthHeader - Monthly",
    showBackground = true,
    widthDp = 360
)
@Composable
fun MonthHeaderMonthlyPreview() {
    var mode by remember { mutableStateOf(CalendarMode.MONTHLY) }

    MaterialTheme {
        MonthHeader(
            yearMonth = YearMonth.of(2026, 1),
            calendarMode = mode,
            onModeChange = { mode = it },
            onMonthArrowClick = {}
        )
    }
}

@Preview(
    name = "MonthHeader - Weekly",
    showBackground = true,
    widthDp = 360
)
@Composable
fun MonthHeaderWeeklyPreview() {
    var mode by remember { mutableStateOf(CalendarMode.WEEKLY) }

    MaterialTheme {
        MonthHeader(
            yearMonth = YearMonth.of(2026, 1),
            calendarMode = mode,
            onModeChange = { mode = it },
            onMonthArrowClick = {}
        )
    }
}