package com.smtm.pickle.presentation.nickname

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun NicknameScreen(
    viewModel: NicknameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NicknameContent(
        uiState = uiState,
        onNicknameChanged = viewModel::onNicknameChanged,
        onCheckDuplicate = viewModel::checkDuplicate,
        onSaveNickname = viewModel::saveNickname
    )
}

@Composable
fun NicknameContent(
    uiState: NicknameUiState,
    modifier: Modifier = Modifier,
    onNicknameChanged: (String) -> Unit = {},
    onCheckDuplicate: () -> Unit = {},
    onSaveNickname: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("닉네임을 설정해주세요")

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = uiState.nickname,
            onValueChange = onNicknameChanged,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.isError
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onCheckDuplicate()

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknamePreview() {
    PickleTheme() {
        NicknameScreen()
    }
}
