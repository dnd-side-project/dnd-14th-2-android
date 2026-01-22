package com.smtm.pickle.presentation.designsystem.components.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeekDaysHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dayOfWeek ->
            val textColor = when (dayOfWeek) {
                DayOfWeek.SUNDAY -> Color.Red.copy(alpha = 0.8f)
                DayOfWeek.SATURDAY -> Color.Blue.copy(alpha = 0.8f)
                else -> MaterialTheme.colorScheme.onSurface
            }

            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekDaysHeaderPreview() {
    MaterialTheme {
        WeekDaysHeader(
            daysOfWeek = listOf(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
            )
        )
    }
}