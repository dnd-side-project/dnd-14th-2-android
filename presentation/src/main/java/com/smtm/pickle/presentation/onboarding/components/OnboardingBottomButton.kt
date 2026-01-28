package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonWithCancel
import com.smtm.pickle.presentation.designsystem.components.button.model.CancelWidth

@Composable
fun OnboardingBottomButton(
    currentPage: Int,
    lastPageIndex: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 14.dp, top = 6.dp),
    ) {
        val isLast = currentPage == lastPageIndex

        if (currentPage > 0) {
            PickleButtonWithCancel(
                confirmText = if (isLast) "시작하기" else "다음",
                cancelText = "이전",
                onConfirmClick = { if (isLast) onFinish() else onNext() },
                onCancelClick = onPrev,
                cancelWidth = CancelWidth.Medium
            )
        } else {
            PickleButton(
                text = "다음",
                onClick = onNext,
            )
        }
    }
}
