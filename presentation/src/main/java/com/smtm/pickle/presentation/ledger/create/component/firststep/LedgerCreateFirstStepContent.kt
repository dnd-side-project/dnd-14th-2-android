package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun LedgerCreateFirstStepContent(
    modifier: Modifier = Modifier,
    amount: String,
    selectedLedgerType: LedgerTypeUi?,
    selectedCategory: CategoryUi?,
    description: String,
    amountChange: (String) -> Unit,
    onLedgerTypeClick: (LedgerTypeUi) -> Unit,
    onCategoryClick: (CategoryUi) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val descriptionScrollExtraPx = with(LocalDensity.current) { 40.dp.toPx() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
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

        LedgerDescriptionInputFiled(
            modifier = Modifier.padding(bottom = 40.dp),
            inputModifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { state ->
                    if (state.isFocused) {
                        scope.launch {
                            awaitFrame()
                            bringIntoViewRequester.bringIntoView()
                            scrollState.animateScrollBy(descriptionScrollExtraPx)
                        }
                    }
                },
            value = description,
            onValueChange = onDescriptionChange,
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
            amountChange = {},
            onLedgerTypeClick = {},
            selectedCategory = null,
            onCategoryClick = {},
            onDescriptionChange = {},
        )
    }
}