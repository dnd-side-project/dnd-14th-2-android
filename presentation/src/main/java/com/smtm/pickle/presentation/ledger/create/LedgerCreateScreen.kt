package com.smtm.pickle.presentation.ledger.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.extension.clearFocusOnBackgroundTab
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateTopBar
import com.smtm.pickle.presentation.ledger.create.component.firststep.LedgerCreateFirstStepContent
import com.smtm.pickle.presentation.ledger.create.component.secondStep.LedgerCreateSecondContent
import java.time.LocalDate

@Composable
fun LedgerCreateScreen(
    viewModel: LedgerCreateViewModel = hiltViewModel(),
    date: LocalDate,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    LedgerCreateEffect.NavigateToHome -> {
                        onNavigateToHome()
                    }
                }
            }
        }
    }

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
        createLedger = viewModel::createLedger,
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
    createLedger: (LocalDate) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PickleTheme.colors.base0)
            .systemBarsPadding()
            .clearFocusOnBackgroundTab(focusManager),
    ) {
        LedgerCreateTopBar(
            text = stringResource(R.string.ledger_create_date_format, date.year, date.monthValue, date.dayOfMonth),
            onNavigationClick = {
                when (uiState.step) {
                    LedgerCreateStep.FIRST -> onNavigateBack()
                    LedgerCreateStep.SECOND -> setStep(LedgerCreateStep.FIRST)
                }
            },
            step = uiState.step
        )

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
                LedgerCreateSecondContent(
                    selectedPaymentMethod = uiState.selectedPaymentMethod,
                    memo = uiState.memo,
                    onSelectedPaymentMethod = selectPaymentMethod,
                    onMemoChange = setMemo,
                    onPreviousClick = { setStep(LedgerCreateStep.FIRST) },
                    onSuccessClick = { createLedger(date) },
                )
            }
        }

    }
}