package com.smtm.pickle.presentation.splash

import androidx.lifecycle.ViewModel
import com.smtm.pickle.domain.usecase.auth.GetAccessTokenUseCase
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.auth.InitTokenUseCase
import com.smtm.pickle.domain.usecase.auth.IsLoggedInUseCase
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
    private val isLoggedInUseCase: IsLoggedInUseCase, // TODO: 사용자 로그인 여부 네비게이션에 사용
    private val initTokenUseCase: InitTokenUseCase,
) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavEvent>(replay = 0)
    val navigationEvent: SharedFlow<NavEvent> = _navigationEvent.asSharedFlow()

    init {
        checkInitialDestination()
        initTokenUseCase
    }

    private fun checkInitialDestination() {
        viewModelScope.launch {
            delay(1500L)

//            val isLoggedIn = isLoggedInUseCase().first()
//
//            when {
//                 -> _navigationEvent.emit(NavEvent.NavigateToOnboarding) // TODO: 첫 실행 시 실행
//                isLoggedIn -> _navigationEvent.emit(NavEvent.NavigateToMain)
//                else -> _navigationEvent.emit(NavEvent.NavigateToLogin)
//            }

            _navigationEvent.emit(NavEvent.NavigateToOnboarding)
        }
    }

    sealed interface NavEvent {
        data object NavigateToOnboarding : NavEvent
        data object NavigateToLogin : NavEvent
        data object NavigateToMain : NavEvent
    }
}
