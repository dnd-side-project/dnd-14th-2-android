package com.smtm.pickle.presentation.designsystem.components.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarPosition
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState

/**
 * ```
 * val snackbarState = remember { SnackbarState() }
 *
 * // scaffold snackbarState 주입
 * Box(modifier = Modifier.fillMaxSize()) {
 *     Scaffold { padding ->
 *         MainScreen(
 *             modifier = Modifier.padding(padding),
 *             snackbarState = snackbarState
 *         )
 *     }
 *
 *     SnackbarHost(
 *         snackbarState = snackbarState
 *     )
 * }
 *
 * // Screen
 * Button(
 *     onClick = {
 *         snackbarState.show(
 *             PickleSnackbar.toastSuccess(
 *                 message = "메시지에 마침표를 찍어요",
 *                 actionLabel = "확인"
 *             )
 *         )
 *     }
 * )
 * ```
 */
@Composable
fun SnackbarHost(
    snackbarState: SnackbarState,
    modifier: Modifier = Modifier,
) {
    val currentSnackbar = snackbarState.currentSnackbar

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = when (currentSnackbar?.position) {
            SnackbarPosition.TOP -> Alignment.TopCenter
            else -> Alignment.BottomCenter
        }
    ) {
        AnimatedVisibility(
            visible = currentSnackbar != null,
            enter = when (currentSnackbar?.position) {
                SnackbarPosition.TOP -> slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))

                else -> slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exit = when (currentSnackbar?.position) {
                SnackbarPosition.TOP -> slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))

                else -> slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            currentSnackbar?.let { snackbar ->
                Box(
                    modifier = Modifier.padding(
                        // TODO: 패딩 조절 필요
                        top = if (snackbar.position == SnackbarPosition.TOP) 106.dp else 0.dp,
                        bottom = if (snackbar.position == SnackbarPosition.BOTTOM) 100.dp else 0.dp
                    )
                ) {
                    PickleSnackbar(
                        snackbarData = snackbar,
                        onDismiss = { snackbarState.dismiss() }
                    )
                }
            }
        }
    }
}
