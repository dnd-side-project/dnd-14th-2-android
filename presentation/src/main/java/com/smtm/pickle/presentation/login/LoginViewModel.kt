package com.smtm.pickle.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.auth.GoogleLoginUseCase
import com.smtm.pickle.domain.usecase.auth.KakaoLoginUseCase
import com.smtm.pickle.domain.usecase.user.GetFirstLoginUseCase
import com.smtm.pickle.domain.usecase.user.SetFirstLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val getFirstLoginUseCase: GetFirstLoginUseCase,
    private val setFirstLoginUseCase: SetFirstLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginWithGoogle() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            runCatching {
                googleLoginUseCase().getOrThrow()
            }.onSuccess {
                val isFirstLogin = getFirstLoginUseCase().first()
                if (isFirstLogin) setFirstLoginUseCase(false)

                _uiState.value = LoginUiState.Success(isFirstLogin)
                Timber.d("Google 로그인 성공")
            }.onFailure { error ->
                _uiState.value = LoginUiState.Error(error.message ?: "Google 로그인 실패")
                Timber.e(error)
            }
        }
    }

    fun loginWithKakao(accessToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            runCatching {
                kakaoLoginUseCase(token = accessToken).getOrThrow()
            }.onSuccess {
                val isFirstLogin = getFirstLoginUseCase().first()
                if (isFirstLogin) setFirstLoginUseCase(false)

                _uiState.value = LoginUiState.Success(isFirstLogin)
                Timber.d("Kakao 로그인 성공")
            }.onFailure { error ->
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
