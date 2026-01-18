package com.smtm.pickle.presentation.designsystem.components.snackbar.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * 커스텀 스낵바 상태
 * @property currentSnackbar 현재 보여지는 스낵바 데이터
 * @property show 스낵바 보여주기
 * @property dismiss 스낵바 숨기기
 * ---
 * ``` kotlin
 * SnackbarState.show(
 *      PickleSnackbar.toastSuccess(
 *          message = "메시지에 마침표를 찍어요",
 *          position = SnackbarPosition.BOTTOM
 *      )
 * )
 * ```
 */
@Stable
class SnackbarState {
    var currentSnackbar by mutableStateOf<SnackbarData?>(null)
        private set

    fun show(snackbarData: SnackbarData) {
        currentSnackbar = snackbarData
    }

    fun dismiss() {
        currentSnackbar = null
    }
}
