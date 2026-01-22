package com.smtm.pickle.presentation.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.CheckNicknameAvailableUseCase
import com.smtm.pickle.domain.usecase.nickname.SaveNicknameUseCase
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

    private val _events = MutableSharedFlow<NicknameEvent>(replay = 0)
    val events: SharedFlow<NicknameEvent> = _events.asSharedFlow()


    /** onValueChange 콜백 함수 */
    fun onNicknameChanged(nickname: String) {
        _uiState.update {
            it.copy(
                nickname = nickname,
                isValidFormat = validateFormat(nickname),
                isCheckingDuplicate = false,
                isAvailable = null
            )
        }
    }

    fun checkDuplicate() {
        val nickname = uiState.value.nickname
        if (!uiState.value.isValidFormat) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCheckingDuplicate = true,
                    isAvailable = null,
                )
            }

            val isAvailable = checkNicknameAvailableUseCase(nickname)

            _uiState.update {
                it.copy(
                    isCheckingDuplicate = true,
                    isAvailable = isAvailable,
                )
            }
        }
    }

    fun saveNickname() {
        viewModelScope.launch {

            runCatching {
                saveNicknameUseCase(uiState.value.nickname)
            }.onSuccess {
                _events.emit(NicknameEvent.NavigateToMain)
            }.onFailure {

            }
        }
    }

    private fun validateFormat(nickname: String): Boolean {
        if (nickname.isBlank()) return false
        if (nickname.length > 5) return false
        if (!nickname.matches(Regex("^[a-z0-9]+$"))) return false
        return true
    }
}

sealed class NicknameEvent() {
    data object NavigateToMain : NicknameEvent()
}
