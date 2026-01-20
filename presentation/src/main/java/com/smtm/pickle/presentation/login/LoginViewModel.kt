package com.smtm.pickle.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.auth.GoogleLoginUseCase
import com.smtm.pickle.domain.usecase.auth.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginWithGoogle() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            googleLoginUseCase()
                .onSuccess { token ->
                    _uiState.value = LoginUiState.Success(isNewUser = false)
                    Timber.d("Google 로그인 성공: ${token.access.take(50)}")
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "Google 로그인 실패")
                    Timber.e(error)
                }
        }
    }

    fun loginWithKakao(accessToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            kakaoLoginUseCase(token = accessToken)
                .onSuccess { token ->
                    _uiState.value = LoginUiState.Success(isNewUser = false)
                    Timber.d("Kakao 로그인 성공: ${token.access.take(50)}")
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "Kakao 로그인 실패")
                    Timber.e(error)
                }
        }
    }

    fun handleLoginError(message: String) {
        _uiState.value = LoginUiState.Error(message)
    }

    fun clearError() {
        _uiState.value = LoginUiState.Idle
    }
}
