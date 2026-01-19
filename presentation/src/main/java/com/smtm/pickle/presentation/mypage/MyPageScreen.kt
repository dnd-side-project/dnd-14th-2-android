package com.smtm.pickle.presentation.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.navigation.navigator.MyPageNavigator

@Composable
fun MyPageScreen(
    navigator: MyPageNavigator,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    MyPageContent(
        onSettingClick = navigator::navigateToSetting,
        onAlarmSettingClick = navigator::navigateToAlarmSetting,
        onLogoutClick = navigator::logout
    )
}

@Composable
private fun MyPageContent(
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit,
    onAlarmSettingClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "MyPage Screen")
            Button(onClick = onSettingClick) {
                Text("세팅 이동")
            }
            Button(onClick = onAlarmSettingClick) {
                Text("알람 세팅 이동")
            }
            Button(onClick = onLogoutClick) {
                Text("로그아웃")
            }
        }
    }
}
