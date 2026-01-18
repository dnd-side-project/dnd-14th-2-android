package com.smtm.pickle.presentation.splash

import androidx.lifecycle.ViewModel
import com.smtm.pickle.domain.usecase.auth.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
) : ViewModel() {

    suspend fun checkAppState(): String {
        delay(1000)

        val token = getTokenUseCase()

        // TODO: 첫 실행 시 온보딩 화면으로 이동 추가
        return when {
            token == null -> "Screen.Login.route"
            else -> "Screen.Home.route"
        }
    }
}
