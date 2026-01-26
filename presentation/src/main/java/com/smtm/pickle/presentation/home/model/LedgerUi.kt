package com.smtm.pickle.presentation.home.model

import java.time.LocalDate

data class LedgerUi(
    val id: Long,
    val type: LedgerTypeUi,
    val amount: String,
    val amountValue: Long,
    val category: CategoryUi,
    val description: String,
    val occurredOn: LocalDate,
    val dateText: String,
    val paymentMethod: PaymentMethodUi,
    val memo: String?
)

data class DailyLedgerUi(
    val date: LocalDate,
    val dateText: String,
    val ledgers: List<LedgerUi>,
    val totalIncome: String?,
    val totalExpense: String?,
)