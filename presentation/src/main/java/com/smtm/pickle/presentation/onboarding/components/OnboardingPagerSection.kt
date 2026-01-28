package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingPagerSection(pagerState: PagerState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp),
            state = pagerState,
        ) {
            OnboardingPageContent()
        }

        Spacer(Modifier.height(29.dp))

        PageIndicator(
            currentPage = pagerState.currentPage,
            pageCount = pagerState.pageCount,
        )
    }
}

@Composable
private fun OnboardingPageContent(
    modifier: Modifier = Modifier,
) {
    // TODO: 컨텐츠 변경
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF5F5F5))
    )
}

// TODO: 추후 디자인 시스템으로 빼기
@Composable
private fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(6.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (isSelected) Color(0xFF2BC4C1) else Color.Gray),
            )
        }
    }
}
