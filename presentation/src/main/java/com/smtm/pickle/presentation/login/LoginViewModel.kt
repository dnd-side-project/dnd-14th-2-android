package com.smtm.pickle.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.auth.SocialLoginType
import com.smtm.pickle.domain.usecase.auth.GetTokenUseCase
import com.smtm.pickle.domain.usecase.auth.LogoutUseCase
import com.smtm.pickle.domain.usecase.auth.SocialLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socialLoginUseCase: SocialLoginUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            val token = getTokenUseCase()
            if (token != null) {
                _uiState.value = LoginUiState.Success
            }
        }
    }

    fun socialLogin(token: String, type: SocialLoginType) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            socialLoginUseCase(token, type)
                .onSuccess {
                    _uiState.value = LoginUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "로그인에 실패했습니다")
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = LoginUiState.Idle
        }
    }
}
