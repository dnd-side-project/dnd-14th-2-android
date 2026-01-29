package com.smtm.pickle.presentation.designsystem.components.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarPosition
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

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
    val snackbar = snackbarState.currentSnackbar ?: return

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = snackbar.position.toAlignment()
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(
                initialOffsetY = {
                    if (snackbar.position.toAlignment() == Alignment.TopCenter) -it else it
                }
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = {
                    if (snackbar.position.toAlignment() == Alignment.TopCenter) -it else it
                }
            ) + fadeOut()
        ) {
            PickleSnackbar(
                snackbarData = snackbar,
                modifier = Modifier.applyPositionPadding(snackbar.position),
                onDismiss = { snackbarState.dismiss() }
            )
        }
    }
}

private fun SnackbarPosition.toAlignment(): Alignment =
    when (this) {
        SnackbarPosition.BelowStatusBar,
        SnackbarPosition.BelowTopAppBar -> Alignment.TopCenter

        SnackbarPosition.AboveSystemNavigation,
        is SnackbarPosition.AboveBottomContents -> Alignment.BottomCenter

        is SnackbarPosition.Custom -> alignment
    }

@Composable
private fun Modifier.applyPositionPadding(position: SnackbarPosition): Modifier =
    when (position) {

        SnackbarPosition.BelowStatusBar ->
            this
                .statusBarsPadding()
                .padding(top = 10.dp)

        SnackbarPosition.BelowTopAppBar ->
            this
                .statusBarsPadding()
                .padding(top = Dimensions.appbarHeight + 10.dp)

        SnackbarPosition.AboveSystemNavigation ->
            this
                .navigationBarsPadding()
                .padding(bottom = 10.dp)

        is SnackbarPosition.AboveBottomContents ->
            this
                .navigationBarsPadding()
                .padding(bottom = 72.dp + 10.dp)

        is SnackbarPosition.Custom ->
            when (position.alignment) {
                Alignment.TopCenter ->
                    this
                        .statusBarsPadding()
                        .padding(top = position.extraPadding)

                Alignment.BottomCenter ->
                    this
                        .navigationBarsPadding()
                        .padding(bottom = position.extraPadding)

                else -> this
            }
    }
