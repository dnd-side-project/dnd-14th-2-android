package com.smtm.pickle.presentation.login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val isFirstLogin: Boolean) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
