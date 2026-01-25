package com.smtm.pickle.presentation.designsystem.components.snackbar.model

/**
 * 커스텀 스낵바 데이터
 * @param message 메시지
 * @param iconType 아이콘 타입
 * @param position 위치 (TOP, BOTTOM)
 * @param actionLabel 액션 텍스트
 * @param onActionClick 액션 클릭 콜백
 * @param duration 지속 시간
 */
data class SnackbarData(
    val message: String,
    val iconType: SnackbarIconType = SnackbarIconType.None,
    val position: SnackbarPosition = SnackbarPosition.BOTTOM,
    val actionLabel: String? = null,
    val onActionClick: (() -> Unit)? = null,
    val duration: SnackbarDuration = SnackbarDuration.TOAST_SHORT,
) {
    companion object {
        fun SnackbarDuration.toMillis(): Long = when (this) {
            SnackbarDuration.TOAST_SHORT -> 2000L
            SnackbarDuration.TOAST_LONG -> 3500L
            SnackbarDuration.SNACKBAR_SHORT -> 4000L
            SnackbarDuration.SNACKBAR_LONG -> 10000L
            SnackbarDuration.SNACKBAR_INDEFINITE -> Long.MAX_VALUE
        }
    }
}
