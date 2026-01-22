package com.smtm.pickle.presentation.onboarding

import android.widget.Toast
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator
import com.smtm.pickle.presentation.onboarding.components.OnboardingBottomButton
import com.smtm.pickle.presentation.onboarding.components.OnboardingPagerSection
import com.smtm.pickle.presentation.onboarding.components.OnboardingScaffold
import com.smtm.pickle.presentation.onboarding.components.OnboardingTopBar
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navigator: AuthNavigator,
    onCompleted: () -> Unit = {},
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
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
        onBack = {},
        onSkipOrFinish = viewModel::completeOnboarding
    )
}

@Composable
private fun OnboardingContent(
    onBack: () -> Unit,
    onSkipOrFinish: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    OnboardingScaffold(
        topContent = { OnboardingTopBar(onBack, onSkipOrFinish) },
        content = { OnboardingPagerSection(pagerState) },
        bottomContent = {
            OnboardingBottomButton(
                currentPage = pagerState.currentPage,
                lastPageIndex = 3,
                onPrev = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                onNext = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
                onFinish = onSkipOrFinish
            )
        }
    )
}

@Preview
@Composable
private fun OnboardingPreview() {
    PickleTheme {
        OnboardingContent(
            onBack = {},
            onSkipOrFinish = {}
        )
    }
}
