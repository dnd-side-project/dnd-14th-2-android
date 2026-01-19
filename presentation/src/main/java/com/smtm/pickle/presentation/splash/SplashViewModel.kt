package com.smtm.pickle.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavEvent>(replay = 0)
    val navigationEvent: SharedFlow<NavEvent> = _navigationEvent.asSharedFlow()

    init {
        checkInitialDestination()
    }

    private fun checkInitialDestination() {
        viewModelScope.launch {
            delay(1500L)
            _navigationEvent.emit(NavEvent.NavigateToOnboarding)
        }
    }

    sealed interface NavEvent {
        data object NavigateToOnboarding : NavEvent
        data object NavigateToLogin : NavEvent
        data object NavigateToMain : NavEvent
    }
}