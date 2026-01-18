package com.smtm.pickle.presentation.designsystem.components.snackbar.model

/**
 * 커스텀 스낵바 지속 시간
 * @param TOAST_SHORT 2초
 * @param TOAST_LONG 3.5초
 * @param SNACKBAR_SHORT 4초
 * @param SNACKBAR_LONG 10초
 * @param SNACKBAR_INDEFINITE 무한
 */
enum class SnackbarDuration {
    TOAST_SHORT,
    TOAST_LONG,
    SNACKBAR_SHORT,
    SNACKBAR_LONG,
    SNACKBAR_INDEFINITE,
}
