package com.smtm.pickle.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLedgerCreate: () -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    HomeContent(
        onNavigateToLedgerCreate = onNavigateToLedgerCreate,
        onNavigateToLedgerDetail = onNavigateToLedgerDetail
    )
}

@Composable
private fun HomeContent(
    onNavigateToLedgerCreate: () -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onNavigateToLedgerCreate) {
                Text("가계부 쓰기")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { onNavigateToLedgerDetail(1L) }) {
                Text("가계부 상세")
            }
        }
    }
}
