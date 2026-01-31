package com.smtm.pickle.presentation.designsystem.components.snackbar.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface SnackbarPosition {

    /** 상태바 바로 아래 */
    data object BelowStatusBar : SnackbarPosition

    /** 앱바 바로 아래 */
    data object BelowTopAppBar : SnackbarPosition

    /** 바텀 네비게이션 위 */
    data object AboveSystemNavigation : SnackbarPosition

    /** 하단 고정 버튼 위 */
    data object AboveBottomContents: SnackbarPosition

    /** 커스텀 */
    data class Custom(
        val alignment: SnackbarAlignment,
        val extraPadding: Dp = 0.dp
    ) : SnackbarPosition
}
