package com.smtm.pickle.presentation.designsystem.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.model.calendar.CalendarMode
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
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 날짜 표시
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        ) {
            Text(
                text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = onMonthArrowClick) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        // 모드 전환
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    val newMode = if (calendarMode == CalendarMode.MONTHLY) {
                        CalendarMode.WEEKLY
                    } else {
                        CalendarMode.MONTHLY
                    }
                    onModeChange(newMode)
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = if (calendarMode == CalendarMode.MONTHLY) "주간 보기" else "월간 보기",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = "주간")
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