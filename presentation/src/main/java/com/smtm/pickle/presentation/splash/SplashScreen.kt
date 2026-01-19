package com.smtm.pickle.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.designsystem.components.PickleIcon
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator

@Composable
fun SplashScreen(
    navigator: AuthNavigator,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    SplashViewModel.NavEvent.NavigateToOnboarding -> navigator.navigateToOnboarding()
                    SplashViewModel.NavEvent.NavigateToLogin -> navigator.navigateToLogin()
                    SplashViewModel.NavEvent.NavigateToMain -> navigator.navigateToMain()
                }
            }
        }
    }

    SplashContent()
}

@Composable
private fun SplashContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PickleIcon()
    }
}
