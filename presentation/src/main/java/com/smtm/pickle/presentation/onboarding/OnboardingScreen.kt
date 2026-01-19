package com.smtm.pickle.presentation.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator

@Composable
fun OnboardingScreen(
    navigator: AuthNavigator,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingContent(
        onCompleteClick = navigator::navigateToLogin
    )
}

@Composable
private fun OnboardingContent(
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Onboarding Screen")
            Button(onClick = onCompleteClick) {
                Text("완료")
            }
        }
    }
}
