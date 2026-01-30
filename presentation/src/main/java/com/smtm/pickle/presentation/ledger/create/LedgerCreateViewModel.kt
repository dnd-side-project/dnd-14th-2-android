package com.smtm.pickle.presentation.ledger.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.Money
import com.smtm.pickle.domain.usecase.ledger.CreateLedgerUseCase
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import com.smtm.pickle.presentation.mapper.ledger.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LedgerCreateViewModel @Inject constructor(
    private val createLedgerUseCase: CreateLedgerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LedgerCreateUiSate())
    val uiState: StateFlow<LedgerCreateUiSate> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LedgerCreateEffect>(replay = 0)
    val sideEffect: SharedFlow<LedgerCreateEffect> = _sideEffect.asSharedFlow()

    fun createLedger(date: LocalDate) {
        viewModelScope.launch {
            val id = (1000..99999).random().toLong()
            val newLedger = Ledger(
                id = LedgerId(id),
                type = _uiState.value.selectedLedgerType!!.toDomain(),
                amount = Money(_uiState.value.amount.toLong()),
                category = _uiState.value.selectedCategory!!.toDomain(),
                description = _uiState.value.description,
                occurredOn = date,
                paymentMethod = _uiState.value.selectedPaymentMethod!!.toDomain(),
                memo = _uiState.value.memo.takeIf { it.isNotEmpty() }
            )

            val ledgerId = createLedgerUseCase(newLedger)

            _sideEffect.emit(LedgerCreateEffect.NavigateToHome)
        }
    }

    fun setStep(step: LedgerCreateStep) {
        _uiState.update { state ->
            when (step) {
                LedgerCreateStep.FIRST -> {
                    state.copy(step = step)
                }

                LedgerCreateStep.SECOND -> {
                    state.copy(
                        step = step,
                        selectedPaymentMethod = null,
                        memo = ""
                    )
                }
            }
        }
    }

    fun setAmount(amount: String) {
        _uiState.update { state -> state.copy(amount = amount) }
    }

    fun selectLedgerType(type: LedgerTypeUi) {
        _uiState.update { state -> state.copy(selectedLedgerType = type) }
    }

    fun selectCategory(category: CategoryUi?) {
        _uiState.update { state -> state.copy(selectedCategory = category) }
    }

    fun setDescription(description: String) {
        _uiState.update { state -> state.copy(description = description) }
    }

    fun selectPaymentMethod(method: PaymentMethodUi) {
        _uiState.update { state -> state.copy(selectedPaymentMethod = method) }
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
    val selectedPaymentMethod: PaymentMethodUi? = null,
    val memo: String = "",
)

sealed interface LedgerCreateEffect {
    data object NavigateToHome : LedgerCreateEffect
}

enum class LedgerCreateStep { FIRST, SECOND }