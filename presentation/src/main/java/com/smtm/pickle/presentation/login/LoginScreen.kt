package com.smtm.pickle.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.common.auth.KakaoLoginManager
import com.smtm.pickle.presentation.designsystem.components.PickleIcon
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.components.ButtonSection
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator

@Composable
fun LoginScreen(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val kakaoLoginManager = remember(context) { KakaoLoginManager(context) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (uiState) {
//            is LoginUiState.Success -> onLoginSuccess((uiState as LoginUiState.Success).isNewUser)
            is LoginUiState.Error -> viewModel.clearError()
            else -> Unit
        }
    }

    LoginContent(
        onGoogleLogin = {
            viewModel.loginWithGoogle()
        },
        onKakaoLogin = {
            kakaoLoginManager.login(
                onSuccess = { accessToken ->
                    viewModel.loginWithKakao(accessToken)
                },
                onFailure = { error ->
                    viewModel.handleLoginError(error.message ?: "Kakao 로그인 실패")
                }
            )
        },
        onLoginClick = navigator::navigateToMain
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onGoogleLogin: () -> Unit,
    onKakaoLogin: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.2f))

        PickleIcon()

        Spacer(modifier = Modifier.weight(1f))

        ButtonSection(
            onGoogleLogin = onGoogleLogin,
            onKakaoLogin = onKakaoLogin,
        )

        Spacer(modifier = Modifier.height(40.dp))

        // TODO: 로그인 구현 후 삭제
        Button(onClick = onLoginClick) { Text("로그인") }
    }

}

@Preview
@Composable
private fun LoginContentPreview() {
    PickleTheme {
        LoginContent(
            onGoogleLogin = {},
            onKakaoLogin = {},
            onLoginClick = {},
        )
    }
}
