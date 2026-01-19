package com.smtm.pickle.presentation.verdict

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
fun VerdictScreen(
    navigator: VerdictNavigator,
    viewModel: VerdictViewModel = hiltViewModel()
) {

    VerdictContent(
        onVerdictItemClick = navigator::navigateToDetail
    )
}

@Composable
private fun VerdictContent(
    modifier: Modifier = Modifier,
    onVerdictItemClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Verdict Screen")
            Button(onClick = onVerdictItemClick) {
                Text("투표 상세 이동")
            }
        }
    }
}
