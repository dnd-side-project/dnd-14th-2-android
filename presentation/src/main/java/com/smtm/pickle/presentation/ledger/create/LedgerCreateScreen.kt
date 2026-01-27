package com.smtm.pickle.presentation.ledger.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.extension.clearFocusOnBackgroundTab
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateBottomBar
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateTopBar
import com.smtm.pickle.presentation.ledger.create.component.firststep.LedgerCreateFirstStepContent
import com.smtm.pickle.presentation.ledger.create.component.secondStep.LedgerCreateSecondContent
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
        selectPaymentMethod = viewModel::selectPaymentMethod,
        setMemo = viewModel::setMemo,
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
    selectPaymentMethod: (PaymentMethodUi) -> Unit,
    setMemo: (String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clearFocusOnBackgroundTab(focusManager),
        containerColor = PickleTheme.colors.base0,
        topBar = {
            LedgerCreateTopBar(
                text = stringResource(R.string.ledger_create_date_format, date.year, date.monthValue, date.dayOfMonth),
                onNavigationClick = onNavigateBack,
                step = uiState.step
            )
        },
        bottomBar = {
            LedgerCreateBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
                step = uiState.step,
                enableNext = with(uiState) {
                    amount.toLongOrNull()?.takeIf { it > 0 } != null &&
                            selectedLedgerType != null &&
                            selectedCategory != null
                },
                enabledSuccess = uiState.selectedPaymentMethod != null,
                onNextClick = { setStep(LedgerCreateStep.SECOND) },
                onPreviousClick = { setStep(LedgerCreateStep.FIRST) },
                onSuccessClick = { },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                    )
                }

                LedgerCreateStep.SECOND -> {
                    LedgerCreateSecondContent(
                        selectedPaymentMethod = uiState.selectedPaymentMethod,
                        memo = uiState.memo,
                        onSelectedPaymentMethod = selectPaymentMethod,
                        onMemoChange = setMemo,
                    )
                }
            }
        }
    }
}