package com.smtm.pickle.presentation.ledger.create

import androidx.lifecycle.ViewModel
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LedgerCreateViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(LedgerCreateUiSate())
    val uiState: StateFlow<LedgerCreateUiSate> = _uiState.asStateFlow()

    fun setStep(step: LedgerCreateStep) {
        _uiState.update { state -> state.copy(step = step) }
    }

    fun setAmount(amount: String) {
        _uiState.update { state -> state.copy(amount = amount) }
    }

    fun selectLedgerType(type: LedgerTypeUi) {
        _uiState.update { state -> state.copy(selectedLedgerType = type) }
    }

    fun selectCategory(category: CategoryUi) {
        _uiState.update { state -> state.copy(selectedCategory = category) }
    }

    fun setDescription(description: String) {
        _uiState.update { state -> state.copy(description = description) }
    }

    fun selectPaymentMethod(method: PaymentMethodUi) {
        _uiState.update { state -> state.copy(paymentMethod = method)}
    }

    fun setMemo(memo: String) {
        _uiState.update { state -> state.copy(memo = memo) }
    }
}

data class LedgerCreateUiSate(
    val step: LedgerCreateStep = LedgerCreateStep.FIRST,
    val amount: String = "",
    val selectedLedgerType: LedgerTypeUi? = null,
    val selectedCategory: CategoryUi? = null,
    val description: String = "",
    val paymentMethod: PaymentMethodUi? = null,
    val memo: String = "",
)

enum class LedgerCreateStep { FIRST, SECOND }