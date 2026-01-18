package com.smtm.pickle.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    onGoogleLogin: () -> Unit,
    onKakaoLogin: () -> Unit
) {
    Column(modifier = modifier) {
        Button(
            onClick = onKakaoLogin,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_kakao),
                contentDescription = "카카오톡 아이콘",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("카카오톡으로 시작하기")
        }

        Button(
            onClick = onGoogleLogin,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "구글 아이콘",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("구글 계정으로 시작하기")
        }
    }
}

@Preview
@Composable
private fun ButtonSectionPreview() {
    PickleTheme {
        ButtonSection(
            onGoogleLogin = {},
            onKakaoLogin = {}
        )
    }
}
