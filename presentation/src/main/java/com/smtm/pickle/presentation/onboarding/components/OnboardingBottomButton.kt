package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingBottomButton(
    currentPage: Int,
    lastPageIndex: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    // TODO: 디자인 시스템 병합 후 버튼 변경
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 14.dp, top = 6.dp),
    ) {
        if (currentPage > 0) {
            Button(
                modifier = Modifier.width(96.dp),
                onClick = onPrev,
            ) {
                Text("이전")
            }
        }

        val isLast = currentPage == lastPageIndex
        Button(
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = { if (isLast) onFinish() else onNext() },
        ) {
            Text(if (isLast) "시작하기" else "다음")
        }
    }
}
