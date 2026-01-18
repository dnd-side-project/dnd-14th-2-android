package com.smtm.pickle.presentation.designsystem.components.textfield.model

import androidx.compose.runtime.Composable
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField

/**
 * 입력 값 처리 상태
 * @property Idle 상태 없음. [PickleTextField]의 supportingText 처리
 * @property Success 성공 시 입력 값 처리
 * @property Error 실패 시 입력 값 처리
 */
sealed class InputState() {
    data object Idle : InputState()
    data class Success(val message: String?) : InputState()
    data class Error(val message: String) : InputState()
}

@Composable
fun InputState.toUiState(
    defaultSupportingText: String?
): Pair<Boolean, String?> {
    return when (this) {
        InputState.Idle -> false to defaultSupportingText

        is InputState.Success -> false to message

        is InputState.Error -> true to message
    }
}
