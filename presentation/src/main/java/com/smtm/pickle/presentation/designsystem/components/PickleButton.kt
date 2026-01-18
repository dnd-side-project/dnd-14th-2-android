package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color = PickleTheme.colors.primary400,
    contentColor: Color = PickleTheme.colors.base0,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Button(
            modifier = modifier
                .height(Dimensions.buttonHeight),
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = PickleTheme.colors.gray100,
                disabledContentColor = PickleTheme.colors.gray600,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = text,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.base0
            )
        }
    }
}

@Composable
fun PickleTwoButton(
    confirmText: String,
    cancelText: String,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
    color: Color = PickleTheme.colors.primary400,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(
            modifier = modifier
                .weight(1f)
                .height(Dimensions.buttonHeight),
            onClick = onCancelClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.gray100,
                contentColor = PickleTheme.colors.gray600,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = cancelText,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray600
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            modifier = modifier
                .weight(2.1f)
                .height(Dimensions.buttonHeight),
            onClick = onConfirmClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = PickleTheme.colors.base0,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = confirmText,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.base0
            )
        }
    }
}

@Preview
@Composable
private fun DefaultButton() {
    Column {
        PickleButton(
            text = "Buttons",
            onClick = {}
        )

        Spacer(Modifier.height(8.dp))

        PickleButton(
            text = "Buttons",
            onClick = {},
            containerColor = PickleTheme.colors.primary500,
        )
    }
}

@Preview
@Composable
private fun TwoButton() {
    PickleTwoButton(
        confirmText = "확인",
        cancelText = "취소",
        onConfirmClick = {},
        onCancelClick = {}
    )
}
