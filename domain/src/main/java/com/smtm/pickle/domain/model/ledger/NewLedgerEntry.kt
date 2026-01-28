package com.smtm.pickle.domain.model.ledger

import java.time.LocalDate

data class NewLedgerEntry(
    val type: LedgerType,
    val amount: Money,
    val category: Category,
    val description: String?,
    val occurredOn: LocalDate,
    val paymentMethod: PaymentMethod,
    val memo: String?,
)