package com.smtm.pickle.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.onboarding.GetOnboardingStatusUseCase
import com.smtm.pickle.domain.usecase.auth.InitTokenUseCase
import com.smtm.pickle.domain.usecase.auth.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
    private val isLoggedInUseCase: IsLoggedInUseCase, // TODO: 사용자 로그인 여부 네비게이션에 사용
    private val initTokenUseCase: InitTokenUseCase,
) : ViewModel() {
    private val _effect = MutableSharedFlow<SplashEffect>(replay = 1)
    val effect: SharedFlow<SplashEffect> = _effect.asSharedFlow()

    init {
        checkInitialDestination()
        viewModelScope.launch {
            initTokenUseCase()
        }
    }

    private fun checkInitialDestination() {
        viewModelScope.launch {
            val isOnboardingCompletedDeferred = async {
                runCatching { getOnboardingStatusUseCase().first() }
                    .getOrDefault(false)
            }

            delay(1500L)

            val navEffect = when {
                !isOnboardingCompletedDeferred.await() -> SplashEffect.NavigateToOnboarding

                // TODO: 로그인 상태 확인 후 로그인 화면 이동 이벤트 발행

                else -> SplashEffect.NavigateToMain
            }

            _effect.emit(navEffect)
        }
    }

    sealed interface SplashEffect {
        data object NavigateToOnboarding : SplashEffect
        data object NavigateToLogin : SplashEffect
        data object NavigateToMain : SplashEffect
    }
}
