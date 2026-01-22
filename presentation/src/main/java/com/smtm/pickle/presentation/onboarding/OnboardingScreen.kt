package com.smtm.pickle.presentation.onboarding

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun OnboardingScreen(
    navigator: AuthNavigator,
    onCompleted: () -> Unit = {},
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collect { effect ->
                when (effect) {
                    is OnboardingEvent.NavigateToLogin -> {
                        Toast.makeText(context, "온보딩 완료\n로그인 화면으로 이동 구현 필요", Toast.LENGTH_SHORT).show()
                        onCompleted()
                    }
                }
            }
        }
    }

    OnboardingContent(
        pages = pages,
        onBack = {},
        onSkipOrFinish = viewModel::completeOnboarding
    )
}

@Composable
private fun OnboardingContent(
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

