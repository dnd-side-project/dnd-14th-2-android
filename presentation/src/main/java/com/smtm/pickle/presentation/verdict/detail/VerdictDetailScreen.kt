package com.smtm.pickle.presentation.verdict.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.navigation.navigator.VerdictNavigator

@Composable
fun VerdictDetailScreen(
    navigator: VerdictNavigator,
    viewModel: VerdictDetailViewModel = hiltViewModel()
) {

    VerdictDetailContent(
        onBackClick = navigator::navigateBack
    )
}

@Composable
private fun VerdictDetailContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Verdict Detail Screen")
            Button(onClick = onBackClick) {
                Text("뒤로가기")
            }
        }
    }
}