package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.LedgerCreateStep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerCreateTopBar(
    modifier: Modifier = Modifier,
    text: String,
    onNavigationClick: () -> Unit,
    step: LedgerCreateStep = LedgerCreateStep.FIRST
) {
    Column(
        modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onNavigationClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_top_navigation_arrow_left),
                    contentDescription = "arrow_left",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified,
                )
            }

            Text(
                text = text,
                style = PickleTheme.typography.head4SemiBold,
                color = PickleTheme.colors.gray800
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            when (step) {
                LedgerCreateStep.FIRST -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(PickleTheme.colors.primary400)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(PickleTheme.colors.gray100)
                    )
                }

                LedgerCreateStep.SECOND -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(PickleTheme.colors.primary400)
                    )
                }
            }
        }
    }
}

@Preview(
    name = "LedgerCreateTopBarPreview - FirstStep",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateTopBarFirstStepPreview() {
    PickleTheme {
        LedgerCreateTopBar(
            text = "2026년 2월 19일",
            onNavigationClick = {},
            step = LedgerCreateStep.FIRST
        )
    }
}

@Preview(
    name = "LedgerCreateTopBarPreview - SecondStep",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateTopBarSecondStepPreview() {
    PickleTheme {
        LedgerCreateTopBar(
            text = "2026년 2월 19일",
            onNavigationClick = {},
            step = LedgerCreateStep.SECOND
        )
    }
}