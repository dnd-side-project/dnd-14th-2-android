package com.smtm.pickle.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.common.auth.KakaoLoginManager
import com.smtm.pickle.presentation.designsystem.components.PickleIcon
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.components.ButtonSection

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (isNewUser: Boolean) -> Unit,
    kakaoLoginManager: KakaoLoginManager,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess((uiState as LoginUiState.Success).isNewUser)
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
        }
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onGoogleLogin: () -> Unit,
    onKakaoLogin: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        PickleIcon()

        Spacer(modifier = Modifier.weight(1f))

        ButtonSection(
            onGoogleLogin = onGoogleLogin,
            onKakaoLogin = onKakaoLogin,
        )
    }
}

@Preview
@Composable
private fun LoginContentPreview() {
    PickleTheme {
        LoginContent(
            onGoogleLogin = {},
            onKakaoLogin = {}
        )
    }
}
