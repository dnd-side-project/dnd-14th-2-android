package com.smtm.pickle.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.onboarding.SetOnboardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingCompleteUseCase: SetOnboardingCompleteUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<OnboardingEvent>(replay = 0)
    val events: SharedFlow<OnboardingEvent> = _events.asSharedFlow()


    fun completeOnboarding() {
        viewModelScope.launch {
            setOnboardingCompleteUseCase()
            _events.emit(OnboardingEvent.NavigateToLogin)
        }
    }

}

sealed interface OnboardingEvent {
    object NavigateToLogin : OnboardingEvent
}
