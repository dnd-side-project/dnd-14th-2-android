package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/** @sample com.smtm.pickle.presentation.designsystem.components.PickleDialogPreview */
@Composable
fun PickleDialog(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(top = 40.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(Dimensions.radiusModal),
        ) {
            Column(
                modifier = Modifier.padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content,
            )
        }
    }
}

/** @sample com.smtm.pickle.presentation.designsystem.components.PickleBottomSheetPreview */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickleBottomSheet(
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    gestureEnable: Boolean = true,
    hasDragHandle: Boolean = true,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        sheetGesturesEnabled = gestureEnable,
        containerColor = PickleTheme.colors.base0,
        shape = RoundedCornerShape(
            topStart = Dimensions.radiusModal,
            topEnd = Dimensions.radiusModal
        ),
        dragHandle = {
            Surface(
                modifier =
                    modifier
                        .padding(vertical = 10.dp)
                        .padding(bottom = 10.dp),
                color = PickleTheme.colors.gray200,
                shape = RoundedCornerShape(Dimensions.radiusFull),
            ) {
                if (hasDragHandle) Box(Modifier.size(width = 48.dp, height = 4.dp))
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            content = content
        )
    }
}

@Preview
@Composable
private fun PickleDialogPreview() {
    PickleTheme {
        PickleDialog(
            onDismiss = {}
        ) {
            PickleButton(
                text = "확인",
                onClick = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
            PickleButton(
                text = "취소",
                onClick = {},
                color = PickleTheme.colors.base0,
                textColor = PickleTheme.colors.gray600
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PickleBottomSheetPreview() {
    PickleTheme {
        PickleBottomSheet(
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { true },
            ),
            onDismiss = {}
        ) {
            Text("즐겨찾는 내역")
            Spacer(modifier = Modifier.height(12.dp))
            PickleButton(
                text = "삭제하기",
                onClick = {}
            )
        }
    }
}
