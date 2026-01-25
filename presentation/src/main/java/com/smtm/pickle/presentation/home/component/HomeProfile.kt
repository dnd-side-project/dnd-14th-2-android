package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.components.button.PickleBadge
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun HomeProfile(
    modifier: Modifier = Modifier,
    badge: String,
    nickname: String,
    income: Long,
    expense: Long,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PickleProfile()

        Spacer(modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            PickleBadge(text = badge)
            Text(
                text = nickname,
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray800
            )
        }

        Spacer(modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "수입",
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray800
                )
                Text(
                    modifier = Modifier.width(130.dp),
                    text = "+${income.toMoneyFormat()}",
                    textAlign = TextAlign.End,
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.primary500
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "지출",
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray800
                )
                Text(
                    modifier = Modifier.width(130.dp),
                    text = "-${expense.toMoneyFormat()}",
                    textAlign = TextAlign.End,
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.error100
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "HomeProfilePreview - Default"
)
@Composable
private fun HomeProfilePreview() {
    PickleTheme {
        HomeProfile(
            badge = "뱃지명",
            nickname = "나의닉네임",
            income = 1000000,
            expense = 500000,
        )
    }
}