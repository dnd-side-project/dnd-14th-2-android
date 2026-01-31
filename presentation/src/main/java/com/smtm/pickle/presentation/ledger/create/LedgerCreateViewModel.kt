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

    private val _uiState = MutableStateFlow(LedgerCreateUiState())
    val uiState: StateFlow<LedgerCreateUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LedgerCreateEffect>(replay = 0)
    val effect: SharedFlow<LedgerCreateEffect> = _effect.asSharedFlow()

    private var isCreating: Boolean = false

    fun createLedger(date: LocalDate) {
        if (isCreating) return
        isCreating = true

        viewModelScope.launch {
            val id = (1000..99999).random().toLong()
            val state = _uiState.value
            val type = state.selectedLedgerType?.toDomain() ?: return@launch
            val amount = state.amount.toLongOrNull()?.takeIf { it > 0 } ?: return@launch
            val category = state.selectedCategory?.toDomain() ?: return@launch
            val paymentMethod = state.selectedPaymentMethod?.toDomain() ?: return@launch

            val newLedger = Ledger(
                id = LedgerId(id),
                type = type,
                amount = Money(amount),
                category = category,
                description = state.description,
                occurredOn = date,
                paymentMethod = paymentMethod,
                memo = state.memo.takeIf { it.isNotEmpty() }
            )

            createLedgerUseCase(newLedger)
                .onSuccess {
                    _effect.emit(LedgerCreateEffect.NavigateToHome)
                }
                .onFailure {
                    _effect.emit(LedgerCreateEffect.ShowSnackBar("저장에 실패했습니다."))
                }

            isCreating = false
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

data class LedgerCreateUiState(
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
    data class ShowSnackBar(val msg: String) : LedgerCreateEffect
}

enum class LedgerCreateStep { FIRST, SECOND }