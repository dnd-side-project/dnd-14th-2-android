package com.smtm.pickle.presentation.ledger.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import java.time.LocalDate

@Composable
fun LedgerCreateScreen(
    date: LocalDate,
    onNavigateBack: () -> Unit,
) {

    LedgerCreateContent(
        date = date,
        onUpButtonClick = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LedgerCreateContent(
    date: LocalDate,
    onUpButtonClick: () -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일",
                        style = PickleTheme.typography.head4SemiBold,
                        color = PickleTheme.colors.gray800
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onUpButtonClick,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_top_navigation_arrow_left),
                            contentDescription = "arrow_left",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Ledger Create Screen")
            }
        }
    }
}