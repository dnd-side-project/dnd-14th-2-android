package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.smtm.pickle.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingTopBar(
    onBack: () -> Unit,
    onSkip: () -> Unit,
) {
    // TODO: 커스텀하기
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(painter = painterResource(R.drawable.ic_topbar_back), contentDescription = null)
            }
        },
        actions = {
            TextButton(onClick = onSkip) {
                Text("건너뛰기")
            }
        },
    )
}
