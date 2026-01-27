package com.smtm.pickle.presentation.ledger.create.component.secondStep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateHeaderText

private const val MEMO_MAX_LENGTH = 100

@Composable
fun LedgerCreateMemo(
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    memo: String,
    onMemoChange: (String) -> Unit,
) {
    val memoLength = memo.length

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        LedgerCreateHeaderText(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            text = stringResource(R.string.ledger_create_memo_header),
        )

        BasicTextField(
            modifier = inputModifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(Dimensions.radius))
                .background(PickleTheme.colors.gray50)
                .padding(12.dp),
            value = memo,
            onValueChange = { new ->
                if (new.length <= MEMO_MAX_LENGTH) {
                    onMemoChange(new)
                }
            },
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (memo.isEmpty()) {
                            Text(
                                text = stringResource(R.string.ledger_create_memo_hint),
                                style = PickleTheme.typography.body3Regular,
                                color = PickleTheme.colors.gray600,
                            )
                        }

                        innerTextField()
                    }

                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = "$memoLength/$MEMO_MAX_LENGTH",
                        style = PickleTheme.typography.body3Regular,
                        color = PickleTheme.colors.gray600,
                    )
                }
            }
        )
    }
}

@Preview(
    name = "LedgerCreateMemoPreview - Memo Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateMemoEmptyPreview() {
    PickleTheme {
        LedgerCreateMemo(
            memo = "",
            onMemoChange = {}
        )
    }
}

@Preview(
    name = "LedgerCreateMemoPreview - Memo Non Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateMemoPreview() {
    PickleTheme {
        LedgerCreateMemo(
            memo = "짱 맛있어염.",
            onMemoChange = {}
        )
    }
}