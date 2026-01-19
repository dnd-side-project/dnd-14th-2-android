package com.smtm.pickle.presentation.mypage.alarmsetting

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
fun AlarmSettingScreen(
    navigator: MyPageNavigator,
    viewModel: AlarmSettingViewModel = hiltViewModel()
) {

    AlarmSettingContent(
        onBackClick = navigator::navigateBack
    )
}

@Composable
private fun AlarmSettingContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Alarm Setting Screen")
            Button(onClick = onBackClick) {
                Text("뒤로가기")
            }
        }
    }
}