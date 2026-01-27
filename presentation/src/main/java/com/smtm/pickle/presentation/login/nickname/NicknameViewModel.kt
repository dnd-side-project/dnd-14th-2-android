package com.smtm.pickle.presentation.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.CheckNicknameAvailableUseCase
import com.smtm.pickle.domain.usecase.nickname.SaveNicknameUseCase
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.splash.SplashViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val checkNicknameAvailableUseCase: CheckNicknameAvailableUseCase,
    private val saveNicknameUseCase: SaveNicknameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NicknameUiState())
    val uiState: StateFlow<NicknameUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavEvent>(replay = 0)
    val navigationEvent: SharedFlow<NavEvent> = _navigationEvent.asSharedFlow()


    /** onValueChange 콜백 함수 */
    fun onNicknameChanged(nickname: String) {
        val correctNickname = nickname.take(MAX_NICKNAME_LENGTH)

        _uiState.update {
            it.copy(
                nickname = correctNickname,
                inputState = validateFormat(correctNickname),
                isCheckingDuplicate = false,
                isAvailable = null
            )
        }
    }

    fun checkDuplicate() {
        val state = uiState.value
        if (state.inputState !is InputState.Success) return
        val requestedNickname = state.nickname

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCheckingDuplicate = true,
                    isAvailable = null,
                )
            }

            val isAvailable = checkNicknameAvailableUseCase(requestedNickname)
            _uiState.update {
                if (it.nickname != requestedNickname) return@update it

                it.copy(
                    isCheckingDuplicate = false,
                    isAvailable = isAvailable,
                    inputState = if (isAvailable) {
                        InputState.Success("사용 가능한 닉네임이에요!")
                    } else {
                        InputState.Error("이미 사용중인 닉네임이에요.")
                    }
                )
            }
        }
    }

    fun saveNickname() {
        viewModelScope.launch {
            runCatching {
                saveNicknameUseCase(uiState.value.nickname)
            }.onSuccess {
                _navigationEvent.emit(NavEvent.NavigateToMain)
            }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _navigationEvent.emit(NavEvent.Back)
        }
    }

    private fun validateFormat(nickname: String): InputState {
        if (nickname.isBlank()) return InputState.Idle
        if (nickname.length > AVAILABLE_LENGTH) return InputState.Error("최대 5자 이내로 설정해주세요.")
        if (!nickname.matches(Regex("^[a-z0-9]+$"))) return InputState.Error("특수 문자 및 영어 대문자는 사용할 수 없어요.")
        return InputState.Success(null)
    }

    private companion object {
        const val MAX_NICKNAME_LENGTH = 30
        const val AVAILABLE_LENGTH = 5
    }

    sealed interface NavEvent {
        data object NavigateToMain : NavEvent
        data object Back : NavEvent
    }
}
