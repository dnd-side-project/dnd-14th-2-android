package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
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
                style = PickleTheme.typography.body1Bold,
                color = PickleTheme.colors.gray700,
            )
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onMonthArrowClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = "arrow_down",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified,
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
        CalendarMode.MONTHLY -> PickleTheme.colors.base0
        CalendarMode.WEEKLY -> PickleTheme.colors.gray700
    }
    val contentColor = when (calendarMode) {
        CalendarMode.MONTHLY -> PickleTheme.colors.gray700
        CalendarMode.WEEKLY -> PickleTheme.colors.base0
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimensions.radiusStadium),
        color = containerColor,
        contentColor = contentColor,
        border = if (calendarMode == CalendarMode.MONTHLY) {
            BorderStroke(width = 1.dp, color = PickleTheme.colors.gray200)
        } else {
            null
        },
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
            Icon(
                modifier = Modifier.size(width = 14.dp, height = 13.dp),
                painter = if (calendarMode == CalendarMode.MONTHLY) {
                    painterResource(R.drawable.ic_arrow_cross_gray)
                } else {
                    painterResource(R.drawable.ic_arrow_cross_white)
                },
                contentDescription = "arrow_cross",
                tint = Color.Unspecified,
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "주간",
                style = PickleTheme.typography.body4Medium
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

    PickleTheme {
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

    PickleTheme {
        MonthHeader(
            yearMonth = YearMonth.of(2026, 1),
            calendarMode = mode,
            onModeChange = { mode = it },
            onMonthArrowClick = {}
        )
    }
}