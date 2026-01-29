package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi

@Composable
fun LedgerCreateFirstStepContent(
    modifier: Modifier = Modifier,
    amount: String,
    selectedLedgerType: LedgerTypeUi?,
    selectedCategory: CategoryUi?,
    description: String,
    onAmountChange: (String) -> Unit,
    onLedgerTypeClick: (LedgerTypeUi) -> Unit,
    onCategoryClick: (CategoryUi) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
    ) {
        LedgerAmountInputField(
            modifier = Modifier.padding(top = 30.dp),
            value = amount,
            onValueChange = onAmountChange,
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

        LedgerDescriptionInputFiled(
            value = description,
            onValueChange = onDescriptionChange,
        )

        Spacer(modifier = Modifier.height(40.dp))

        Spacer(modifier = Modifier.weight(1f))

        LedgerCreateFirstBottomButton(
            enableNext = amount.toLongOrNull()?.takeIf { it > 0 } != null &&
                    selectedLedgerType != null &&
                    selectedCategory != null,
            onNextClick = onNextClick,
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
            description = "",
            onAmountChange = {},
            onLedgerTypeClick = {},
            selectedCategory = null,
            onCategoryClick = {},
            onDescriptionChange = {},
            onNextClick = {}
        )
    }
}