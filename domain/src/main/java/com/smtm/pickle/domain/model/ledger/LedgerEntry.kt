package com.smtm.pickle.domain.model.ledger

import java.time.LocalDate

@JvmInline
value class LedgerId(val value: Long)

@JvmInline
value class Money(val value: Long) {
    init {
        require(value >= 0) { "Money must be >= 0" }
    }
}

data class LedgerEntry(
    val id: LedgerId,
    val type: LedgerType,
    val amount: Money,
    val category: Category,
    val description: String,
    val occurredOn: LocalDate,
    val paymentMethod: PaymentMethod? = null,
    val memo: String? = null,
)

enum class LedgerType { INCOME, EXPENSE }

enum class PaymentMethod {
    BANK_TRANSFER, CARD, CASH, OTHER
}

enum class Category {
    FOOD,
    TRANSPORT,
    HOUSING,
    SHOPPING,
    HEALTH_MEDICAL,
    EDUCATION_SELF_DEVELOPMENT,
    LEISURE_HOBBY,
    SAVING_FINANCE,
    OTHER,
}