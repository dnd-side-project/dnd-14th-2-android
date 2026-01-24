package com.smtm.pickle.presentation.createledger

import com.smtm.pickle.presentation.model.ledger.Category
import com.smtm.pickle.presentation.model.ledger.LedgerType
import com.smtm.pickle.presentation.model.ledger.PaymentType
import java.time.LocalDate

data class CreateLedgerUiState(
    // 현재 Step
    val currentStep: CreateLedgerStep = CreateLedgerStep.STEP1,

    // 공통
    val date: LocalDate = LocalDate.now(),

    // Step 1 데이터
    val amount: String = "",  // 사용자 입력이라 String으로 관리
    val ledgerType: LedgerType = LedgerType.EXPENSE,
    val category: Category? = null,
    val title: String = "",

    // Step 2 데이터
    val paymentType: PaymentType? = null,
    val memo: String = "",

    // 상태
    val isLoading: Boolean = false,
    val isSubmitSuccess: Boolean = false,
    val errorMessage: String? = null,
) {
    // Step 1 → Step 2 이동 가능 여부
    val canProceedToStep2: Boolean
        get() = amount.isNotBlank() &&
                amount.toLongOrNull() != null &&
                amount.toLong() > 0 &&
                category != null &&
                title.isNotBlank()

    // 최종 제출 가능 여부
    val canSubmit: Boolean
        get() = canProceedToStep2 && paymentType != null
}
enum class CreateLedgerStep {
    STEP1, STEP2
}