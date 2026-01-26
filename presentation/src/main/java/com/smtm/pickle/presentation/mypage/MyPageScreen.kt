package com.smtm.pickle.presentation.mypage

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
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    onNavigateMyLedger: () -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateAlarmSetting: () -> Unit,
) {
    MyPageContent(
        onNavigateMyLedger = onNavigateMyLedger,
        onNavigateSetting = onNavigateSetting,
        onNavigateAlarmSetting = onNavigateAlarmSetting,
    )
}

@Composable
private fun MyPageContent(
    onNavigateMyLedger: () -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateAlarmSetting: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Green.copy(0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onNavigateMyLedger) {
                Text("내 가계부")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onNavigateSetting) {
                Text("설정 이동")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onNavigateAlarmSetting) {
                Text("알림 설정 이동")
            }
        }
    }
}
