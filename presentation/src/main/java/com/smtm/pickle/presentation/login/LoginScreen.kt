package com.smtm.pickle.presentation.login

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
fun LoginScreen(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginContent(
        onLoginClick = navigator::navigateToMain
    )
}

@Composable
private fun LoginContent(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Login Screen")
            Button(onClick = onLoginClick) {
                Text("로그인")
            }
        }
    }
}