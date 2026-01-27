package com.smtm.pickle.presentation.ledger.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateTopBar
import com.smtm.pickle.presentation.ledger.create.component.firststep.LedgerCreateFirstStepContent
import java.time.LocalDate

@Composable
fun LedgerCreateScreen(
    date: LocalDate,
    onNavigateBack: () -> Unit,
) {

    LedgerCreateContent(
        date = date,
        onNavigateBack = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LedgerCreateContent(
    date: LocalDate,
    onNavigateBack: () -> Unit,
) {
    var amount by remember { mutableStateOf("0") }
    var selectedLedgerType by remember { mutableStateOf<LedgerTypeUi?>(null) }
    var selectedCategory by remember { mutableStateOf<CategoryUi?>(null) }
    var content by remember { mutableStateOf("") }
    val enableNext = amount.takeIf { it.toLong() > 0 }.isNullOrEmpty().not() && selectedLedgerType != null && selectedCategory != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PickleTheme.colors.base0
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LedgerCreateTopBar(
                text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일",
                onNavigationClick = onNavigateBack
            )

            Spacer(modifier = Modifier.height(32.dp))

            LedgerCreateFirstStepContent(
                amount = amount,
                selectedLedgerType = selectedLedgerType,
                selectedCategory = selectedCategory,
                content = content,
                amountChange = {},
                onLedgerTypeClick = {},
                onCategoryClick = {},
                onContentChange = {},
                onNextClick = {},
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateContentPreview() {
    PickleTheme {
        LedgerCreateContent(
            date = LocalDate.now(),
            onNavigateBack = {}
        )
    }
}