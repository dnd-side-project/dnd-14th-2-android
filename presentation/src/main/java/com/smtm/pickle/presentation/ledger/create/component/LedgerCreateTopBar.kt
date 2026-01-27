package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerCreateTopBar(
    modifier: Modifier = Modifier,
    text: String,
    onNavigationClick: () -> Unit,
) {
    Column(
        modifier = modifier
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
                Image(
                    painter = painterResource(R.drawable.ic_top_navigation_arrow_left),
                    contentDescription = "arrow_left",
                    modifier = Modifier.size(48.dp)
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
            Box(
                modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(PickleTheme.colors.primary400)
            )
            Box(
                modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(PickleTheme.colors.gray100)
            )
        }
    }

}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateTopBarPreview() {
    PickleTheme {
        LedgerCreateTopBar(
            text = "2026년 2월 19일",
            onNavigationClick = {}
        )
    }
}