package com.smtm.pickle.presentation.createledger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.presentation.model.ledger.Category
import com.smtm.pickle.presentation.model.ledger.LedgerType
import com.smtm.pickle.presentation.model.ledger.PaymentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateLedgerViewModel @Inject constructor(
    // private val createLedgerUseCase: CreateLedgerUseCase  // 나중에 추가
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateLedgerUiState())
    val uiState: StateFlow<CreateLedgerUiState> = _uiState.asStateFlow()

    // ========== Step 1 ==========

    fun setAmount(amount: String) {
        // 숫자만 허용
        val filtered = amount.filter { it.isDigit() }
        _uiState.update { it.copy(amount = filtered) }
    }

    fun setLedgerType(type: LedgerType) {
        _uiState.update { it.copy(ledgerType = type) }
    }

    fun setCategory(category: Category) {
        _uiState.update { it.copy(category = category) }
    }

    fun setTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    // ========== Step 2 ==========

    fun setPaymentType(paymentType: PaymentType) {
        _uiState.update { it.copy(paymentType = paymentType) }
    }

    fun setMemo(memo: String) {
        _uiState.update { it.copy(memo = memo) }
    }

    // ========== Navigation ==========

    fun goToStep2() {
        if (_uiState.value.canProceedToStep2) {
            _uiState.update { it.copy(currentStep = CreateLedgerStep.STEP2) }
        }
    }

    fun goToStep1() {
        _uiState.update { it.copy(currentStep = CreateLedgerStep.STEP1) }
    }

    // ========== Submit ==========

    fun submit() {
        if (!_uiState.value.canSubmit) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // TODO: UseCase 호출
                // val request = CreateLedgerRequest(
                //     amount = _uiState.value.amount.toLong(),
                //     ledgerType = _uiState.value.ledgerType,
                //     category = _uiState.value.category!!,
                //     title = _uiState.value.title,
                //     paymentType = _uiState.value.paymentType!!,
                //     memo = _uiState.value.memo,
                //     date = _uiState.value.date,
                // )
                // createLedgerUseCase(request)

                _uiState.update { it.copy(isLoading = false, isSubmitSuccess = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }
}