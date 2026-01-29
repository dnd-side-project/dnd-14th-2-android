package com.smtm.pickle.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.onboarding.GetOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase
) : ViewModel() {
    private val _effect = MutableSharedFlow<SplashEffect>(replay = 0)
    val effect: SharedFlow<SplashEffect> = _effect.asSharedFlow()

    init {
        checkInitialDestination()
    }

    private fun checkInitialDestination() {
        viewModelScope.launch {
            delay(1500L)

            val isOnboardingCompleted = getOnboardingStatusUseCase().first()

            when {
                !isOnboardingCompleted -> _effect.emit(SplashEffect.NavigateToOnboarding)

                // TODO: 로그인 상태 확인 후 로그인 화면 이동 이벤트 발행

                else -> _effect.emit(SplashEffect.NavigateToMain)
            }
        }
    }

    sealed interface SplashEffect {
        data object NavigateToOnboarding : SplashEffect
        data object NavigateToLogin : SplashEffect
        data object NavigateToMain : SplashEffect
    }
}
