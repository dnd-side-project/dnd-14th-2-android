package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi

@Composable
fun LedgerCreateFirstStepContent(
    modifier: Modifier = Modifier,
    amount: String,
    selectedLedgerType: LedgerTypeUi?,
    selectedCategory: CategoryUi?,
    content: String,
    amountChange: (String) -> Unit,
    onLedgerTypeClick: (LedgerTypeUi) -> Unit,
    onCategoryClick: (CategoryUi) -> Unit,
    onContentChange: (String) -> Unit,
    onNextClick: () -> Unit
) {
    val enableNext = amount.takeIf { it.toLong() > 0 }.isNullOrEmpty().not() && selectedLedgerType != null && selectedCategory != null

    Column(modifier = modifier) {
        LedgerAmountInputField(
            value = amount,
            onValueChange = amountChange,
        )

        LedgerTypeSelectors(
            selectedType = selectedLedgerType,
            onLedgerTypeClick = onLedgerTypeClick,
        )

        LedgerCategorySelectors(
            selectedCategory = selectedCategory,
            onCategoryClick = onCategoryClick,
        )

        Spacer(modifier = Modifier.height(20.dp))

        LedgerContentInputFiled(
            value = content,
            onValueChange = onContentChange,
        )

        Spacer(modifier = Modifier.weight(1f))
        PickleButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            text = "다음",
            onClick = onNextClick,
            enabled = enableNext,
            color = if (enableNext) PickleTheme.colors.primary400 else PickleTheme.colors.gray100,
            textColor = if (enableNext) PickleTheme.colors.base0 else PickleTheme.colors.gray600,
        )
    }
}

@Preview(
    name = "LedgerCreateFirstStepContentPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateFirstStepContentPreview() {
    PickleTheme {
        LedgerCreateFirstStepContent(
            amount = "0",
            selectedLedgerType = null,
            content = "",
            amountChange = {},
            onLedgerTypeClick = {},
            selectedCategory = null,
            onCategoryClick = {},
            onContentChange = {},
            onNextClick = {},
        )
    }
}