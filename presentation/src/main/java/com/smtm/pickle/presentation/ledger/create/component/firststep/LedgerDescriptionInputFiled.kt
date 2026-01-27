package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun LedgerDescriptionInputFiled(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val borderColor = if (value.isEmpty()) {
        PickleTheme.colors.transparent
    } else {
        PickleTheme.colors.primary400
    }
    val borderWidth = if (value.isEmpty()) 0.dp else 1.5.dp

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "가계부 내용을 입력해주세요.",
            style = PickleTheme.typography.head4SemiBold,
            color = PickleTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            modifier = Modifier
                .border(
                    shape = RoundedCornerShape(Dimensions.radius),
                    width = borderWidth,
                    color = borderColor
                )
                .clip(RoundedCornerShape(Dimensions.radius))
                .background(PickleTheme.colors.gray50)
                .padding(horizontal = 16.dp, vertical = 13.dp),
            value = value,
            textStyle = PickleTheme.typography.body3Regular.copy(color = PickleTheme.colors.gray800),
            onValueChange = { value ->
                if (value.length <= 15) {
                    onValueChange(value)
                }
            },
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "공백 포함 15자 이내로 입력해주세요.",
                                style = PickleTheme.typography.body3Regular,
                                color = PickleTheme.colors.gray600,
                            )
                        }
                        innerTextField()
                    }

                    if (value.isNotEmpty()) {
                        Row {
                            Spacer(modifier = Modifier.width(4.dp))
                            Image(
                                painter = painterResource(R.drawable.ic_ledger_content_close),
                                contentDescription = "clear",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { onValueChange("") }
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview(
    name = "LedgerDescriptionInputFiledPreview - Value Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerDescriptionInputFiledEmptyPreview() {
    PickleTheme {
        LedgerDescriptionInputFiled(
            value = "",
            onValueChange = {}
        )
    }
}

@Preview(
    name = "LedgerDescriptionInputFiledPreview - Value Non Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerDescriptionInputFiledPreview() {
    PickleTheme {
        LedgerDescriptionInputFiled(
            value = "가나다라마바사아자차카타파하.",
            onValueChange = {}
        )
    }
}