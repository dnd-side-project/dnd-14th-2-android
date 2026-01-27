package com.smtm.pickle.presentation.ledger.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateTopBar
import com.smtm.pickle.presentation.ledger.create.component.firststep.LedgerCreateFirstStepContent
import java.time.LocalDate

@Composable
fun LedgerCreateScreen(
    viewModel: LedgerCreateViewModel = hiltViewModel(),
    date: LocalDate,
    onNavigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LedgerCreateContent(
        date = date,
        uiState = uiState,
        setAmount = viewModel::setAmount,
        selectLedgerType = viewModel::selectLedgerType,
        selectCategory = viewModel::selectCategory,
        setDescription = viewModel::setDescription,
        setStep = viewModel::setStep,
        onNavigateBack = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LedgerCreateContent(
    date: LocalDate,
    uiState: LedgerCreateUiSate,
    setAmount: (String) -> Unit,
    selectLedgerType: (LedgerTypeUi) -> Unit,
    selectCategory: (CategoryUi) -> Unit,
    setDescription: (String) -> Unit,
    setStep: (LedgerCreateStep) -> Unit,
    onNavigateBack: () -> Unit,
) {

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

            when (uiState.step) {
                LedgerCreateStep.FIRST -> {
                    LedgerCreateFirstStepContent(
                        amount = uiState.amount,
                        selectedLedgerType = uiState.selectedLedgerType,
                        selectedCategory = uiState.selectedCategory,
                        description = uiState.description,
                        amountChange = setAmount,
                        onLedgerTypeClick = selectLedgerType,
                        onCategoryClick = selectCategory,
                        onDescriptionChange = setDescription,
                        onNextClick = { setStep(LedgerCreateStep.SECOND) },
                    )
                }

                LedgerCreateStep.SECOND -> {

                }
            }
        }
    }
}