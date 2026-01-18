package com.smtm.pickle.presentation.designsystem.components.snackbar.model

/**
 * 커스텀 스낵바 아이콘 타입
 * - Figma 디자인 상의 성공, 실패 아이콘 사용
 *
 * @property SUCCESS 성공
 * @property ERROR 실패
 * @property NONE 없음
 * @property CUSTOM 커스텀 아이콘 (`iconRes: Int`)
 */
sealed class SnackbarIconType() {
    data object SUCCESS : SnackbarIconType()
    data object ERROR : SnackbarIconType()
    data object NONE : SnackbarIconType()
    data class CUSTOM(val iconRes: Int) : SnackbarIconType()
}
