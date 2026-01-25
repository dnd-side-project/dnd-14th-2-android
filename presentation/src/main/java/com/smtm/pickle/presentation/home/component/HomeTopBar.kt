package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onStatisticsClick: () -> Unit,
    onAlarmClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "LOGO")

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onStatisticsClick
        ) {
            Image(
                painter = painterResource(R.drawable.ic_statistics),
                contentDescription = "statistics",
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onAlarmClick
        ) {
            Image(
                painter = painterResource(R.drawable.ic_alarm_new),
                contentDescription = "statistics",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "HomeTopBar - New Alarm")
@Composable
private fun HomeTopBarPreview() {
    PickleTheme {
        HomeTopBar(
            onStatisticsClick = {},
            onAlarmClick = {}
        )
    }
}