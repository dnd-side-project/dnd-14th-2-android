package com.smtm.pickle.presentation.createledger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.createledger.component.Step1Content
import com.smtm.pickle.presentation.createledger.component.Step2Content
import com.smtm.pickle.presentation.model.ledger.Category
import com.smtm.pickle.presentation.model.ledger.LedgerType
import com.smtm.pickle.presentation.model.ledger.PaymentType
import java.time.format.DateTimeFormatter

@Composable
fun CreateLedgerScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreateLedgerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // 제출 성공 시 뒤로가기
    LaunchedEffect(uiState.isSubmitSuccess) {
        if (uiState.isSubmitSuccess) {
            onNavigateBack()
        }
    }

    CreateLedgerContent(
        uiState = uiState,
        onAmountChange = viewModel::setAmount,
        onLedgerTypeChange = viewModel::setLedgerType,
        onCategorySelect = viewModel::setCategory,
        onTitleChange = viewModel::setTitle,
        onPaymentTypeSelect = viewModel::setPaymentType,
        onMemoChange = viewModel::setMemo,
        onNextClick = viewModel::goToStep2,
        onPreviousClick = viewModel::goToStep1,
        onSubmitClick = viewModel::submit,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateLedgerContent(
    uiState: CreateLedgerUiState,
    onAmountChange: (String) -> Unit,
    onLedgerTypeChange: (LedgerType) -> Unit,
    onCategorySelect: (Category) -> Unit,
    onTitleChange: (String) -> Unit,
    onPaymentTypeSelect: (PaymentType) -> Unit,
    onMemoChange: (String) -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onSubmitClick: () -> Unit,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = uiState.date.format(dateFormatter))
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState.currentStep) {
                CreateLedgerStep.STEP1 -> {
                    Step1Content(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        amount = uiState.amount,
                        ledgerType = uiState.ledgerType,
                        selectedCategory = uiState.category,
                        title = uiState.title,
                        canProceed = uiState.canProceedToStep2,
                        onAmountChange = onAmountChange,
                        onLedgerTypeChange = onLedgerTypeChange,
                        onCategorySelect = onCategorySelect,
                        onTitleChange = onTitleChange,
                        onNextClick = onNextClick,
                    )
                }

                CreateLedgerStep.STEP2 -> {
                    Step2Content(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        selectedPaymentType = uiState.paymentType,
                        memo = uiState.memo,
                        canSubmit = uiState.canSubmit,
                        isLoading = uiState.isLoading,
                        onPaymentTypeSelect = onPaymentTypeSelect,
                        onMemoChange = onMemoChange,
                        onPreviousClick = onPreviousClick,
                        onSubmitClick = onSubmitClick,
                    )
                }
            }
        }
    }
}