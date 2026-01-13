package com.smtm.pickle.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
)

@Composable
fun OnboardingScreen() {
    val pages = remember {
        listOf(
            OnboardingPage(
                title = "가계부 입력 기능",
                description = "지출을 기록하고 한눈에 확인해요."
            ),
            OnboardingPage(
                title = "소비 습관 기능",
                description = "내 소비 패턴을 분석해서 보여줘요."
            ),
            OnboardingPage(
                title = "지원 연동 기능",
                description = "필요한 기능들과 연동해 편하게 써요."
            )
        )
    }

    OnBoardingRoute(
        pages = pages,
        onBack = {},
        onSkipOrFinish = {}
    )
}

@Composable
private fun OnBoardingRoute(
    pages: List<OnboardingPage>,
    onBack: () -> Unit,
    onSkipOrFinish: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            OnboardingTopBar(
                onBack = {
                    if (pagerState.currentPage == 0) onBack()
                    else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                },
                onSkip = onSkipOrFinish
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(Modifier.height(8.dp))

                HorizontalPager(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    state = pagerState,
                ) { page ->
                    OnboardingPageContent(
                        modifier = Modifier.fillMaxSize(),
                        page = pages[page],
                    )
                }

                Spacer(Modifier.height(12.dp))
                PageIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    pageCount = pages.size,
                    currentPage = pagerState.currentPage,
                )

                Spacer(Modifier.height(16.dp))
                BottomButton(
                    currentPage = pagerState.currentPage,
                    lastPageIndex = pages.lastIndex,
                    onPrev = {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    },
                    onNext = {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    },
                    onFinish = onSkipOrFinish
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingTopBar(
    onBack: () -> Unit,
    onSkip: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            TextButton(onClick = onBack) {
                Text("Back")
            }
        },
        actions = {
            TextButton(onClick = onSkip) {
                Text("건너뛰기")
            }
        },
    )
}

@Composable
private fun OnboardingPageContent(
    modifier: Modifier = Modifier,
    page: OnboardingPage,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Image")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun PageIndicator(
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
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .size(if (isSelected) 8.dp else 6.dp)
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFBDBDBDBD)),
            )
        }
    }
}

@Composable
private fun BottomButton(
    modifier: Modifier = Modifier,
    currentPage: Int,
    lastPageIndex: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (currentPage > 0) {
            OutlinedButton(
                modifier = Modifier.width(88.dp),
                onClick = onPrev,
            ) {
                Text("이전")
            }
        }

        val isLast = currentPage == lastPageIndex
        Button(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            onClick = { if (isLast) onFinish() else onNext() },
        ) {
            Text(if (isLast) "시작하기" else "다음")
        }
    }
}